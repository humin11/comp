! function (window) {
    function Element() {
        this.initialize = function () {
            this.elementType = "element", this.serializedProperties = ["elementType"], this.propertiesStack = [], this._id = "" + (new Date).getTime()
        }, this.distroy = function () {}, this.removeHandler = function () {}, this.attr = function (a, b) {
            if (null != a && null != b) {
                this[a] = b
            } else {
                if (null != a) {
                    return this[a]
                }
            }
            return this
        }, this.save = function () {
            var a = this,
                b = {};
            this.serializedProperties.forEach(function (c) {
                b[c] = a[c]
            }), this.propertiesStack.push(b)
        }, this.restore = function () {
            var a, b;
            null != this.propertiesStack && 0 != this.propertiesStack.length && (a = this, b = this.propertiesStack.pop(), this.serializedProperties.forEach(function (c) {
                a[c] = b[c]
            }))
        }, this.toJson = function () {
            var a = this,
                b = "{",
                c = this.serializedProperties.length;
            return this.serializedProperties.forEach(function (d, e) {
                var f = a[d];
                "string" == typeof f && (f = '"' + f + '"'), b += '"' + d + '":' + f, c > e + 1 && (b += ",")
            }), b += "}"
        }
    }
    JTopo = {
        version: "0.4.3",
        zIndex_Container: 1,
        zIndex_Link: 2,
        zIndex_Node: 3,
        SceneMode: {
            normal: "normal",
            drag: "drag",
            edit: "edit",
            select: "select"
        },
        MouseCursor: {
            normal: "default",
            pointer: "pointer",
            top_left: "nw-resize",
            top_center: "n-resize",
            top_right: "ne-resize",
            middle_left: "e-resize",
            middle_right: "e-resize",
            bottom_left: "ne-resize",
            bottom_center: "n-resize",
            bottom_right: "nw-resize",
            move: "move",
            open_hand: "url(./img/cur/openhand.cur) 8 8, default",
            closed_hand: "url(./img/cur/closedhand.cur) 8 8, default"
        },
        createStageFromJson: function (jsonStr, canvas) {
            var stage, k, scenes;
            eval("var jsonObj = " + jsonStr), stage = new JTopo.Stage(canvas);
            for (k in jsonObj) {
                "childs" != k && (stage[k] = jsonObj[k])
            }
            return scenes = jsonObj.childs, scenes.forEach(function (a) {
                var c, d, b = new JTopo.Scene(stage);
                for (c in a) {
                    "childs" != c && (b[c] = a[c]), "background" == c && (b.background = a[c])
                }
                d = a.childs, d.forEach(function (a) {
                    var e, c = null,
                        d = a.elementType;
                    "node" == d ? c = new JTopo.Node : "CircleNode" == d && (c = new JTopo.CircleNode);
                    for (e in a) {
                        c[e] = a[e]
                    }
                    b.add(c)
                })
            }), stage
        }
    }, JTopo.Element = Element, window.JTopo = JTopo
}(window),
function (JTopo) {
    function MessageBus(a) {
        var b = this;
        this.name = a, this.messageMap = {}, this.messageCount = 0, this.subscribe = function (a, d) {
            var e = b.messageMap[a];
            null == e && (b.messageMap[a] = []), b.messageMap[a].push(d), b.messageCount++
        }, this.unsubscribe = function (a) {
            var c = b.messageMap[a];
            null != c && (b.messageMap[a] = null, delete b.messageMap[a], b.messageCount--)
        }, this.publish = function (a, c, d) {
            var f, e = b.messageMap[a];
            if (null != e) {
                for (f = 0; f < e.length; f++) {
                    d ? function (a, b) {
                        setTimeout(function () {
                            a(b)
                        }, 10)
                    }(e[f], c) : e[f](c)
                }
            }
        }
    }

    function getDistance(a, b, c, d) {
        var e, f;
        return null == c && null == d ? (e = b.x - a.x, f = b.y - a.y) : (e = c - a, f = d - b), Math.sqrt(e * e + f * f)
    }

    function getElementsBound(a) {
        var c, d, b = {
            left: Number.MAX_VALUE,
            right: Number.MIN_VALUE,
            top: Number.MAX_VALUE,
            bottom: Number.MIN_VALUE
        };
        for (c = 0; c < a.length; c++) {
            d = a[c], d instanceof JTopo.Link || (b.left > d.x && (b.left = d.x, b.leftNode = d), b.right < d.x + d.width && (b.right = d.x + d.width, b.rightNode = d), b.top > d.y && (b.top = d.y, b.topNode = d), b.bottom < d.y + d.height && (b.bottom = d.y + d.height, b.bottomNode = d))
        }
        return b.width = b.right - b.left, b.height = b.bottom - b.top, b
    }

    function mouseCoords(a) {
        return a = cloneEvent(a), a.pageX || a.pageY ? (a.x = a.pageX, a.y = a.pageY) : (a.x = a.clientX + document.body.scrollLeft - document.body.clientLeft, a.y = a.clientY + document.body.scrollTop - document.body.clientTop), a
    }

    function getEventPosition(a) {
        return a = cloneEvent(a) || mouseCoords(window.event), a.x = document.body.scrollLeft + (a.x || a.layerX), a.y = document.body.scrollTop + (a.y || a.layerY), JTopo.util.isIE && (a.x += document.documentElement.scrollLeft, a.y += document.documentElement.scrollTop), a
    }

    function rotatePoint(a, b, c, d, e) {
        var f = c - a,
            g = d - b,
            h = Math.sqrt(f * f + g * g),
            i = Math.atan2(g, f) + e;
        return {
            x: a + Math.cos(i) * h,
            y: b + Math.sin(i) * h
        }
    }

    function rotatePoints(a, b, c) {
        var e, f, d = [];
        for (e = 0; e < b.length; e++) {
            f = rotatePoint(a.x, a.y, b[e].x, b[e].y, c), d.push(f)
        }
        return d
    }

    function $foreach(a, b, c) {
        function e(d) {
            d != a.length && (b(a[d]), setTimeout(function () {
                e(++d)
            }, c))
        }
        if (0 != a.length) {
            var d = 0;
            e(d)
        }
    }

    function $for(a, b, c, d) {
        function f(a) {
            a != b && (c(b), setTimeout(function () {
                f(++a)
            }, d))
        }
        if (!(a > b)) {
            var e = 0;
            f(e)
        }
    }

    function cloneEvent(a) {
        var c, b = {};
        for (c in a) {
            "returnValue" != c && "keyLocation" != c && (b[c] = a[c])
        }
        return b
    }

    function clone(a) {
        var c, b = {};
        for (c in a) {
            b[c] = a[c]
        }
        return b
    }

    function isPointInRect(a, b) {
        var c = b.x,
            d = b.y,
            e = b.width,
            f = b.height;
        return a.x > c && a.x < c + e && a.y > d && a.y < d + f
    }

    function isPointInLine(a, b, c) {
        var d = JTopo.util.getDistance(b, c),
            e = JTopo.util.getDistance(b, a),
            f = JTopo.util.getDistance(c, a),
            g = Math.abs(e + f - d) <= 0.5;
        return g
    }

    function removeFromArray(a, b) {
        var c, d;
        for (c = 0; c < a.length; c++) {
            if (d = a[c], d === b) {
                a = a.del(c);
                break
            }
        }
        return a
    }

    function randomColor() {
        return Math.floor(255 * Math.random()) + "," + Math.floor(255 * Math.random()) + "," + Math.floor(255 * Math.random())
    }

    function isIntsect() {}

    function getProperties(a, b) {
        var d, e, c = "";
        for (d = 0; d < b.length; d++) {
            d > 0 && (c += ","), e = a[b[d]], "string" == typeof e ? e = '"' + e + '"' : void 0 == e && (e = null), c += b[d] + ":" + e
        }
        return c
    }

    function loadStageFromJson(json, canvas) {
        var k, scenes, i, sceneObj, scene, p, nodeMap, elements, m, elementObj, type, element, mk, obj = eval(json),
            stage = new JTopo.Stage(canvas);
        for (k in stageObj) {
            if ("scenes" != k) {
                stage[k] = obj[k]
            } else {
                for (scenes = obj.scenes, i = 0; i < scenes.length; i++) {
                    sceneObj = scenes[i], scene = new JTopo.Scene(stage);
                    for (p in sceneObj) {
                        if ("elements" != p) {
                            scene[p] = sceneObj[p]
                        } else {
                            for (nodeMap = {}, elements = sceneObj.elements, m = 0; m < elements.length; m++) {
                                elementObj = elements[m], type = elementObj.elementType, "Node" == type && (element = new JTopo.Node);
                                for (mk in elementObj) {
                                    element[mk] = elementObj[mk]
                                }
                                nodeMap[element.text] = element, scene.add(element)
                            }
                        }
                    }
                }
            }
        }
        return console.log(stage), stage
    }

    function toJson(a) {
        var e, f, g, h, b = "backgroundColor,visible,mode,rotate,alpha,scaleX,scaleY,shadow,translateX,translateY,areaSelect,paintAll".split(","),
            c = "text,elementType,x,y,width,height,visible,alpha,rotate,scaleX,scaleY,fillColor,shadow,transformAble,zIndex,dragable,selected,showSelected,font,fontColor,textPosition,textOffsetX,textOffsetY".split(","),
            d = "{";
        for (d += "frames:" + a.frames, d += ", scenes:[", e = 0; e < a.childs.length; e++) {
            for (f = a.childs[e], d += "{", d += getProperties(f, b), d += ", elements:[", g = 0; g < f.childs.length; g++) {
                h = f.childs[g], g > 0 && (d += ","), d += "{", d += getProperties(h, c), d += "}"
            }
            d += "]}"
        }
        return d += "]", d += "}"
    }

    function changeColor(a, b, c, d, e) {
        var h, i, j, k, l, m, f = canvas.width = b.width,
            g = canvas.height = b.height;
        for (a.clearRect(0, 0, canvas.width, canvas.height), a.drawImage(b, 0, 0), h = a.getImageData(0, 0, b.width, b.height), i = h.data, j = 0; f > j; j++) {
            for (k = 0; g > k; k++) {
                l = 4 * (j + k * f), 0 != i[l + 3] && (null != c && (i[l + 0] += c), null != d && (i[l + 1] += d), null != e && (i[l + 2] += e))
            }
        }
        return a.putImageData(h, 0, 0, 0, 0, b.width, b.height), m = canvas.toDataURL(), alarmImageCache[b.src] = m, m
    }

    function genImageAlarm(a, b) {
        null == b && (b = 255);
        try {
            if (alarmImageCache[a.src]) {
                return alarmImageCache[a.src]
            }
            var c = new Image;
            return c.src = changeColor(graphics, a, b), alarmImageCache[a.src] = c, c
        } catch (d) {}
        return null
    }

    function getOffsetPosition(a) {
        var b, c, d, e, f, g, h, i;
        if (!a) {
            return {
                left: 0,
                top: 0
            }
        }
        if (b = 0, c = 0, "getBoundingClientRect" in document.documentElement) {
            d = a.getBoundingClientRect(), e = a.ownerDocument, f = e.body, g = e.documentElement, h = g.clientTop || f.clientTop || 0, i = g.clientLeft || f.clientLeft || 0, b = d.top + (self.pageYOffset || g && g.scrollTop || f.scrollTop) - h, c = d.left + (self.pageXOffset || g && g.scrollLeft || f.scrollLeft) - i
        } else {
            do {
                b += a.offsetTop || 0, c += a.offsetLeft || 0, a = a.offsetParent
            } while (a)
        }
        return {
            left: c,
            top: b
        }
    }
    var canvas, graphics, alarmImageCache;
    requestAnimationFrame = window.requestAnimationFrame || window.mozRequestAnimationFrame || window.webkitRequestAnimationFrame || window.msRequestAnimationFrame || window.oRequestAnimationFrame || function (a) {
        setTimeout(a, 1000 / 24)
    }, Array.prototype.del = function (a) {
        if ("number" != typeof a) {
            for (var b = 0; b < this.length; b++) {
                if (this[b] === a) {
                    return this.slice(0, b).concat(this.slice(b + 1, this.length))
                }
            }
            return this
        }
        return 0 > a ? this : this.slice(0, a).concat(this.slice(a + 1, this.length))
    }, [].indexOf || (Array.prototype.indexOf = function (a) {
        for (var b = 0; b < this.length; b++) {
            if (this[b] === a) {
                return b
            }
        }
        return -1
    }), window.console || (window.console = {
        log: function () {},
        info: function () {},
        debug: function () {},
        warn: function () {},
        error: function () {}
    }), canvas = document.createElement("canvas"), graphics = canvas.getContext("2d"), alarmImageCache = {}, JTopo.util = {
        rotatePoint: rotatePoint,
        rotatePoints: rotatePoints,
        getDistance: getDistance,
        getEventPosition: getEventPosition,
        mouseCoords: mouseCoords,
        MessageBus: MessageBus,
        isFirefox: navigator.userAgent.indexOf("Firefox") > 0,
        isIE: !(!window.attachEvent || -1 !== navigator.userAgent.indexOf("Opera")),
        clone: clone,
        isPointInRect: isPointInRect,
        isPointInLine: isPointInLine,
        removeFromArray: removeFromArray,
        cloneEvent: cloneEvent,
        randomColor: randomColor,
        isIntsect: isIntsect,
        toJson: toJson,
        loadStageFromJson: loadStageFromJson,
        getElementsBound: getElementsBound,
        genImageAlarm: genImageAlarm,
        getOffsetPosition: getOffsetPosition
    }, window.$for = $for, window.$foreach = $foreach
}(JTopo),
function (e) {
    function d(b) {
        return {
            hgap: 16,
            visible: !1,
            exportCanvas: document.createElement("canvas"),
            getImage: function (a, l) {
                var h, k = b.getBound(),
                    j = 1,
                    i = 1;
                return this.exportCanvas.width = b.canvas.width, this.exportCanvas.height = b.canvas.height, null != a && null != l ? (this.exportCanvas.width = a, this.exportCanvas.height = l, j = a / k.width, i = l / k.height) : (k.width > b.canvas.width && (this.exportCanvas.width = k.width), k.height > b.canvas.height && (this.exportCanvas.height = k.height)), h = this.exportCanvas.getContext("2d"), b.childs.length > 0 && (h.save(), h.clearRect(0, 0, this.exportCanvas.width, this.exportCanvas.height), b.childs.forEach(function (c) {
                    1 == c.visible && (c.save(), c.translateX = 0, c.translateY = 0, c.scaleX = 1, c.scaleY = 1, h.scale(j, i), k.left < 0 && (c.translateX = Math.abs(k.left)), k.top < 0 && (c.translateY = Math.abs(k.top)), c.paintAll = !0, c.repaint(h), c.paintAll = !1, c.restore())
                }), h.restore()), this.exportCanvas.toDataURL("image/png")
            },
            canvas: document.createElement("canvas"),
            update: function () {
                this.eagleImageDatas = this.getData(b)
            },
            setSize: function (g, c) {
                this.width = this.canvas.width = g, this.height = this.canvas.height = c
            },
            getData: function (x, w) {
                function u(h) {
                    var g = h.stage.canvas.width,
                        k = h.stage.canvas.height,
                        j = g / h.scaleX / 2,
                        i = k / h.scaleY / 2;
                    return {
                        translateX: h.translateX + j - j * h.scaleX,
                        translateY: h.translateY + i - i * h.scaleY
                    }
                }
                var v, t, s, r, q, p, o, n;
                if (null != p && null != o ? this.setSize(x, w) : this.setSize(200, 160), v = this.canvas.getContext("2d"), b.childs.length > 0) {
                    v.save(), v.clearRect(0, 0, this.canvas.width, this.canvas.height), b.childs.forEach(function (c) {
                        1 == c.visible && (c.save(), c.centerAndZoom(null, null, v), c.repaint(v), c.restore())
                    }), t = u(b.childs[0]), s = t.translateX * this.canvas.width / b.canvas.width * b.childs[0].scaleX, r = t.translateY * this.canvas.height / b.canvas.height * b.childs[0].scaleY, q = b.getBound(), p = b.canvas.width / b.childs[0].scaleX / q.width, o = b.canvas.height / b.childs[0].scaleY / q.height, p > 1 && (p = 1), o > 1 && (p = 1), s *= p, r *= o, q.left < 0 && (s -= Math.abs(q.left) * (this.width / q.width)), q.top < 0 && (r -= Math.abs(q.top) * (this.height / q.height)), v.save(), v.lineWidth = 1, v.strokeStyle = "rgba(255,0,0,1)", v.strokeRect(-s, -r, v.canvas.width * p, v.canvas.height * o), v.restore(), n = null;
                    try {
                        n = v.getImageData(0, 0, v.canvas.width, v.canvas.height)
                    } catch (a) {}
                    return n
                }
                return null
            },
            paint: function () {
                if (null != this.eagleImageDatas) {
                    var a = b.graphics;
                    a.save(), a.fillStyle = "rgba(211,211,211,0.3)", a.fillRect(b.canvas.width - this.canvas.width - 2 * this.hgap, b.canvas.height - this.canvas.height - 1, b.canvas.width - this.canvas.width, this.canvas.height + 1), a.fill(), a.putImageData(this.eagleImageDatas, b.canvas.width - this.canvas.width - this.hgap, b.canvas.height - this.canvas.height), a.restore()
                } else {
                    this.eagleImageDatas = this.getData(b)
                }
            },
            eventHandler: function (t, s, r) {
                var o, n, m, l, k, q = s.x,
                    p = s.y;
                return q > r.canvas.width - this.canvas.width && p > r.canvas.height - this.canvas.height ? (q = s.x - this.canvas.width, p = s.y - this.canvas.height, "mousedown" == t && (this.lastTranslateX = r.childs[0].translateX, this.lastTranslateY = r.childs[0].translateY), "mousedrag" == t && r.childs.length > 0 && (o = s.dx, n = s.dy, m = r.getBound(), l = this.canvas.width / r.childs[0].scaleX / m.width, k = this.canvas.height / r.childs[0].scaleY / m.height, r.childs[0].translateX = this.lastTranslateX - o / l, r.childs[0].translateY = this.lastTranslateY - n / k), void 0) : void 0
            }
        }
    }

    function f(D) {
        function A(g) {
            var i = e.util.getEventPosition(g),
                h = e.util.getOffsetPosition(C.canvas);
            return i.offsetLeft = i.x - h.left, i.offsetTop = i.y - h.top, i.x = i.offsetLeft, i.y = i.offsetTop, i.target = null, i
        }

        function z(g) {
            B = !1, document.onselectstart = function () {
                return !1
            }, this.mouseOver = !0;
            var c = A(g);
            C.dispatchEventToScenes("mouseover", c), C.dispatchEvent("mouseover", c)
        }

        function y(g) {
            B = !0, document.onselectstart = function () {
                return !0
            };
            var c = A(g);
            C.dispatchEventToScenes("mouseout", c), C.dispatchEvent("mouseout", c), C.needRepaint = 0 == C.animate ? !1 : !0
        }

        function x(g) {
            var c = A(g);
            C.mouseDown = !0, C.mouseDownX = c.x, C.mouseDownY = c.y, C.dispatchEventToScenes("mousedown", c), C.dispatchEvent("mousedown", c)
        }

        function w(g) {
            var c = A(g);
            C.dispatchEventToScenes("mouseup", c), C.dispatchEvent("mouseup", c), C.mouseDown = !1, C.needRepaint = 0 == C.animate ? !1 : !0
        }

        function v(g) {
            var c = A(g);
            C.mouseDown ? 0 == g.button && (c.dx = c.x - C.mouseDownX, c.dy = c.y - C.mouseDownY, C.dispatchEventToScenes("mousedrag", c), C.dispatchEvent("mousedrag", c), 1 == C.eagleEye.visible && C.eagleEye.update()) : (C.dispatchEventToScenes("mousemove", c), C.dispatchEvent("mousemove", c))
        }

        function u(g) {
            var c = A(g);
            C.dispatchEventToScenes("click", c), C.dispatchEvent("click", c)
        }

        function t(g) {
            var c = A(g);
            C.dispatchEventToScenes("dbclick", c), C.dispatchEvent("dbclick", c)
        }

        function s(g) {
            var c = A(g);
            C.dispatchEventToScenes("mousewheel", c), C.dispatchEvent("mousewheel", c), null != C.wheelZoom && (g.preventDefault ? g.preventDefault() : (g = g || window.event, g.returnValue = !1), 1 == C.eagleEye.visible && C.eagleEye.update())
        }

        function r(c) {
            e.util.isIE || !window.addEventListener ? (c.onmouseout = y, c.onmouseover = z, c.onmousedown = x, c.onmouseup = w, c.onmousemove = v, c.onclick = u, c.ondblclick = t, c.onmousewheel = s, c.touchstart = x, c.touchmove = v, c.touchend = w) : (c.addEventListener("mouseout", y), c.addEventListener("mouseover", z), c.addEventListener("mousedown", x), c.addEventListener("mouseup", w), c.addEventListener("mousemove", v), c.addEventListener("click", u), c.addEventListener("dblclick", t), e.util.isFirefox ? c.addEventListener("DOMMouseScroll", s) : c.addEventListener("mousewheel", s)), window.addEventListener && (window.addEventListener("keydown", function (g) {
                C.dispatchEventToScenes("keydown", e.util.cloneEvent(g));
                var h = g.keyCode;
                (37 == h || 38 == h || 39 == h || 40 == h) && (g.preventDefault ? g.preventDefault() : (g = g || window.event, g.returnValue = !1))
            }, !0), window.addEventListener("keyup", function (g) {
                C.dispatchEventToScenes("keyup", e.util.cloneEvent(g));
                var h = g.keyCode;
                (37 == h || 38 == h || 39 == h || 40 == h) && (g.preventDefault ? g.preventDefault() : (g = g || window.event, g.returnValue = !1))
            }, !0))
        }
        var C, B, b, a;
        e.stage = this, C = this, this.initialize = function (g) {
                r(g), this.canvas = g, this.graphics = g.getContext("2d"), this.childs = [], this.frames = 24, this.messageBus = new e.util.MessageBus, this.eagleEye = d(this), this.wheelZoom = null, this.mouseDownX = 0, this.mouseDownY = 0, this.mouseDown = !1, this.mouseOver = !1, this.needRepaint = !0, this.serializedProperties = ["frames", "wheelZoom"]
            }, null != D && this.initialize(D), B = !0, document.oncontextmenu = function () {
                return B
            }, this.dispatchEventToScenes = function (h, g) {
                var j, i;
                return 0 != this.frames && (this.needRepaint = !0), 1 == this.eagleEye.visible && -1 != h.indexOf("mouse") && (j = g.x, i = g.y, j > this.width - this.eagleEye.width && i > this.height - this.eagleEye.height) ? (this.eagleEye.eventHandler(h, g, this), void 0) : (this.childs.forEach(function (l) {
                    if (1 == l.visible) {
                        var k = l[h + "Handler"];
                        if (null == k) {
                            throw new Error("Function not found:" + h + "Handler")
                        }
                        k.call(l, g)
                    }
                }), void 0)
            }, this.add = function (g) {
                for (var c = 0; c < this.childs.length; c++) {
                    if (this.childs[c] === g) {
                        return
                    }
                }
                g.addTo(this), this.childs.push(g)
            }, this.remove = function (g) {
                if (null == g) {
                    throw new Error("Stage.remove出错: 参数为null!")
                }
                for (var c = 0; c < this.childs.length; c++) {
                    if (this.childs[c] === g) {
                        return g.stage = null, this.childs = this.childs.del(c), this
                    }
                }
                return this
            }, this.clear = function () {
                this.childs = []
            }, this.addEventListener = function (h, g) {
                var j = this,
                    i = function (c) {
                        g.call(j, c)
                    };
                return this.messageBus.subscribe(h, i), this
            }, this.removeEventListener = function (c) {
                this.messageBus.unsubscribe(c)
            }, this.removeAllEventListener = function () {
                this.messageBus = new e.util.MessageBus
            }, this.dispatchEvent = function (g, c) {
                return this.messageBus.publish(g, c), this
            }, b = "click,dbclick,mousedown,mouseup,mouseover,mouseout,mousemove,mousedrag,mousewheel,touchstart,touchmove,touchend".split(","), a = this, b.forEach(function (c) {
                a[c] = function (g) {
                    null != g ? this.addEventListener(c, g) : this.dispatchEvent(c)
                }
            }), this.saveImageInfo = function (h, g) {
                var j = this.eagleEye.getImage(h, g),
                    i = window.open("about:blank");
                return i.document.write("<img src='" + j + "' alt='from canvas'/>"), this
            }, this.saveAsLocalImage = function (h, g) {
                var i = this.eagleEye.getImage(h, g);
                return i.replace("image/png", "image/octet-stream"), window.location.href = i, this
            }, this.paint = function () {
                null != this.canvas && (this.graphics.save(), this.graphics.clearRect(0, 0, this.width, this.height), this.childs.forEach(function (c) {
                    1 == c.visible && c.repaint(C.graphics)
                }), 1 == this.eagleEye.visible && this.eagleEye.paint(this), this.graphics.restore())
            }, this.repaint = function () {
                0 != this.frames && (this.frames < 0 && 0 == this.needRepaint || (this.paint(), this.frames < 0 && (this.needRepaint = !1)))
            }, this.zoom = function (c) {
                this.childs.forEach(function (g) {
                    0 != g.visible && g.zoom(c)
                })
            }, this.zoomOut = function (c) {
                this.childs.forEach(function (g) {
                    0 != g.visible && g.zoomOut(c)
                })
            }, this.zoomIn = function (c) {
                this.childs.forEach(function (g) {
                    0 != g.visible && g.zoomIn(c)
                })
            }, this.centerAndZoom = function () {
                this.childs.forEach(function (c) {
                    0 != c.visible && c.centerAndZoom()
                })
            }, this.setCenter = function (h, g) {
                var i = this;
                this.childs.forEach(function (k) {
                    var j = h - i.canvas.width / 2,
                        c = g - i.canvas.height / 2;
                    k.translateX = -j, k.translateY = -c
                })
            }, this.getBound = function () {
                var c = {
                    left: Number.MAX_VALUE,
                    right: Number.MIN_VALUE,
                    top: Number.MAX_VALUE,
                    bottom: Number.MIN_VALUE
                };
                return this.childs.forEach(function (g) {
                    var h = g.getElementsBound();
                    h.left < c.left && (c.left = h.left, c.leftNode = h.leftNode), h.top < c.top && (c.top = h.top, c.topNode = h.topNode), h.right > c.right && (c.right = h.right, c.rightNode = h.rightNode), h.bottom > c.bottom && (c.bottom = h.bottom, c.bottomNode = h.bottomNode)
                }), c.width = c.right - c.left, c.height = c.bottom - c.top, c
            }, this.toJson = function () {
                var g = this,
                    h = '{"version":"' + e.version + '",';
                return this.serializedProperties.length, this.serializedProperties.forEach(function (c) {
                    var i = g[c];
                    "string" == typeof i && (i = '"' + i + '"'), h += '"' + c + '":' + i + ","
                }), h += '"childs":[', this.childs.forEach(function (c) {
                    h += c.toJson()
                }), h += "]", h += "}"
            },
            function () {
                0 == C.frames ? setTimeout(arguments.callee, 100) : C.frames < 0 ? (C.repaint(), setTimeout(arguments.callee, 1000 / -C.frames)) : (C.repaint(), setTimeout(arguments.callee, 1000 / C.frames))
            }(), setTimeout(function () {
                C.mousewheel(function (g) {
                    var c = null == g.wheelDelta ? g.detail : g.wheelDelta;
                    null != this.wheelZoom && (c > 0 ? this.zoomIn(this.wheelZoom) : this.zoomOut(this.wheelZoom))
                }), C.paint()
            }, 300), setTimeout(function () {
                C.paint()
            }, 1000), setTimeout(function () {
                C.paint()
            }, 3000)
    }
    f.prototype = {
        get width() {
            return this.canvas.width
        }, get height() {
            return this.canvas.height
        }, set cursor(b) {
            this.canvas.style.cursor = b
        }, get cursor() {
            return this.canvas.style.cursor
        }, set mode(b) {
            this.childs.forEach(function (a) {
                a.mode = b
            })
        }
    }, e.Stage = f
}(JTopo),
function (e) {
    function d(j) {
        function h(k, g, m, l) {
            return function (c) {
                c.beginPath(), c.strokeStyle = "rgba(0,0,236,0.5)", c.fillStyle = "rgba(0,0,236,0.1)", c.rect(k, g, m, l), c.fill(), c.stroke(), c.closePath()
            }
        }
        var b, a, i = this;
        return this.initialize = function () {
            d.prototype.initialize.apply(this, arguments), this.messageBus = new e.util.MessageBus, this.elementType = "scene", this.childs = [], this.zIndexMap = {}, this.zIndexArray = [], this.backgroundColor = "255,255,255", this.visible = !0, this.alpha = 0, this.scaleX = 1, this.scaleY = 1, this.mode = e.SceneMode.normal, this.translate = !0, this.translateX = 0, this.translateY = 0, this.lastTranslateX = 0, this.lastTranslateY = 0, this.mouseDown = !1, this.mouseDownX = null, this.mouseDownY = null, this.mouseDownEvent = null, this.areaSelect = !0, this.operations = [], this.selectedElements = [], this.paintAll = !1;
            var g = "background,backgroundColor,mode,paintAll,areaSelect,translate,translateX,translateY,lastTranslatedX,lastTranslatedY,alpha,visible,scaleX,scaleY".split(",");
            this.serializedProperties = this.serializedProperties.concat(g)
        }, this.initialize(), this.setBackground = function (c) {
            this.background = c
        }, this.addTo = function (c) {
            this.stage !== c && null != c && (this.stage = c)
        }, null != j && (j.add(this), this.addTo(j)), this.show = function () {
            this.visible = !0
        }, this.hide = function () {
            this.visible = !1
        }, this.paint = function (g) {
            if (0 != this.visible && null != this.stage) {
                if (g.save(), this.paintBackgroud(g), g.restore(), g.save(), g.scale(this.scaleX, this.scaleY), 1 == this.translate) {
                    var c = this.getOffsetTranslate(g);
                    g.translate(c.translateX, c.translateY)
                }
                this.paintChilds(g), g.restore(), g.save(), this.paintOperations(g, this.operations), g.restore()
            }
        }, this.repaint = function (c) {
            0 != this.visible && this.paint(c)
        }, this.paintBackgroud = function (c) {
            null != this.background ? c.drawImage(this.background, 0, 0, c.canvas.width, c.canvas.height) : (c.beginPath(), c.fillStyle = "rgba(" + this.backgroundColor + "," + this.alpha + ")", c.fillRect(0, 0, c.canvas.width, c.canvas.height), c.closePath())
        }, this.getDisplayedElements = function () {
            var g, o, n, m, l, k = [];
            for (g = 0; g < this.zIndexArray.length; g++) {
                for (o = this.zIndexArray[g], n = this.zIndexMap[o], m = 0; m < n.length; m++) {
                    l = n[m], this.isVisiable(l) && k.push(l)
                }
            }
            return k
        }, this.getDisplayedNodes = function () {
            var l, k, g = [];
            for (l = 0; l < this.childs.length; l++) {
                k = this.childs[l], k instanceof e.Node && this.isVisiable(k) && g.push(k)
            }
            return g
        }, this.paintChilds = function (c) {
            var p, o, n, m, l, k;
            for (p = 0; p < this.zIndexArray.length; p++) {
                for (o = this.zIndexArray[p], n = this.zIndexMap[o], m = 0; m < n.length; m++) {
                    l = n[m], (1 == this.paintAll || this.isVisiable(l)) && (c.save(), 1 == l.transformAble && (k = l.getCenterLocation(), c.translate(k.x, k.y), l.rotate && c.rotate(l.rotate), l.scaleX && l.scaleY ? c.scale(l.scaleX, l.scaleY) : l.scaleX ? c.scale(l.scaleX, 1) : l.scaleY && c.scale(1, l.scaleY)), 1 == l.shadow && (c.shadowBlur = l.shadowBlur, c.shadowColor = l.shadowColor, c.shadowOffsetX = l.shadowOffsetX, c.shadowOffsetY = l.shadowOffsetY), l instanceof e.InteractiveElement && (l.selected && 1 == l.showSelected && l.paintSelected(c), 1 == l.isMouseOver && l.paintMouseover(c)), l.paint(c), c.restore())
                }
            }
        }, this.getOffsetTranslate = function (k) {
            var n, m, l, g = this.stage.canvas.width,
                o = this.stage.canvas.height;
            return null != k && "move" != k && (g = k.canvas.width, o = k.canvas.height), n = g / this.scaleX / 2, m = o / this.scaleY / 2, l = {
                translateX: this.translateX + (n - n * this.scaleX),
                translateY: this.translateY + (m - m * this.scaleY)
            }
        }, this.isVisiable = function (k) {
            var p, o, n, m, l;
            return 1 != k.visible ? !1 : k instanceof e.Link ? !0 : (p = this.getOffsetTranslate(), o = k.x + p.translateX, n = k.y + p.translateY, o *= this.scaleX, n *= this.scaleY, m = o + k.width * this.scaleX, l = n + k.height * this.scaleY, o > this.stage.canvas.width || n > this.stage.canvas.height || 0 > m || 0 > l ? !1 : !0)
        }, this.paintOperations = function (k, g) {
            for (var l = 0; l < g.length; l++) {
                g[l](k)
            }
        }, this.findElements = function (k) {
            var l, g = [];
            for (l = 0; l < this.childs.length; l++) {
                1 == k(this.childs[l]) && g.push(this.childs[l])
            }
            return g
        }, this.getElementsByClass = function (c) {
            return this.findElements(function (g) {
                return g instanceof c
            })
        }, this.addOperation = function (c) {
            return this.operations.push(c), this
        }, this.clearOperations = function () {
            return this.operations = [], this
        }, this.getElementByXY = function (k, r) {
            var p, o, n, m, l, q = null;
            for (p = this.zIndexArray.length - 1; p >= 0; p--) {
                for (o = this.zIndexArray[p], n = this.zIndexMap[o], m = n.length - 1; m >= 0; m--) {
                    if (l = n[m], l instanceof e.InteractiveElement && this.isVisiable(l) && l.isInBound(k, r)) {
                        return q = l
                    }
                }
            }
            return q
        }, this.add = function (c) {
            this.childs.push(c), null == this.zIndexMap[c.zIndex] && (this.zIndexMap[c.zIndex] = [], this.zIndexArray.push(c.zIndex), this.zIndexArray.sort(function (k, g) {
                return k - g
            })), this.zIndexMap["" + c.zIndex].push(c)
        }, this.remove = function (g) {
            this.childs = e.util.removeFromArray(this.childs, g);
            var k = this.zIndexMap[g.zIndex];
            k && (this.zIndexMap[g.zIndex] = e.util.removeFromArray(k, g)), g.removeHandler(this)
        }, this.clear = function () {
            var c = this;
            this.childs.forEach(function (g) {
                g.removeHandler(c)
            }), this.childs = [], this.operations = [], this.zIndexArray = [], this.zIndexMap = {}
        }, this.addToSelected = function (c) {
            this.selectedElements.push(c)
        }, this.cancleAllSelected = function (g) {
            for (var c = 0; c < this.selectedElements.length; c++) {
                this.selectedElements[c].unselectedHandler(g)
            }
            this.selectedElements = []
        }, this.notInSelectedNodes = function (g) {
            for (var c = 0; c < this.selectedElements.length; c++) {
                if (g === this.selectedElements[c]) {
                    return !1
                }
            }
            return !0
        }, this.removeFromSelected = function (k) {
            var g, l;
            for (g = 0; g < this.selectedElements.length; g++) {
                l = this.selectedElements[g], k === l && (this.selectedElements = this.selectedElements.del(g))
            }
        }, this.toSceneEvent = function (g) {
            var k, l = e.util.clone(g);
            return l.x /= this.scaleX, l.y /= this.scaleY, 1 == this.translate && (k = this.getOffsetTranslate(), l.x -= k.translateX, l.y -= k.translateY), null != l.dx && (l.dx /= this.scaleX, l.dy /= this.scaleY), null != this.currentElement && (l.target = this.currentElement), l
        }, this.selectElement = function (k) {
            var m, l, g = i.getElementByXY(k.x, k.y);
            if (null != g) {
                if (k.target = g, g.mousedownHander(k), g.selectedHandler(k), i.notInSelectedNodes(g)) {
                    k.ctrlKey || i.cancleAllSelected(), i.addToSelected(g)
                } else {
                    for (1 == k.ctrlKey && (g.unselectedHandler(), this.removeFromSelected(g)), m = 0; m < this.selectedElements.length; m++) {
                        l = this.selectedElements[m], l.selectedHandler()
                    }
                }
            } else {
                k.ctrlKey || i.cancleAllSelected()
            }
            this.currentElement = g
        }, this.mousedownHandler = function (g) {
            var k = this.toSceneEvent(g);
            if (this.mouseDown = !0, this.mouseDownX = k.x, this.mouseDownY = k.y, this.mouseDownEvent = k, this.mode == e.SceneMode.normal) {
                this.selectElement(k), (null == this.currentElement || this.currentElement instanceof e.Link) && 1 == this.translate && (this.lastTranslateX = this.translateX, this.lastTranslateY = this.translateY)
            } else {
                if (this.mode == e.SceneMode.drag && 1 == this.translate) {
                    return this.lastTranslateX = this.translateX, this.lastTranslateY = this.translateY, void 0
                }
                this.mode == e.SceneMode.select && this.selectElement(k)
            }
            i.dispatchEvent("mousedown", k)
        }, this.mouseupHandler = function (g) {
            this.stage.cursor != e.MouseCursor.normal && (this.stage.cursor = e.MouseCursor.normal), i.clearOperations();
            var k = this.toSceneEvent(g);
            null != this.currentElement && (k.target = i.currentElement, this.currentElement.mouseupHandler(k)), this.dispatchEvent("mouseup", k), this.mouseDown = !1
        }, this.dragElements = function (k) {
            var p, o, n, m, l;
            if (null != this.currentElement && 1 == this.currentElement.dragable) {
                for (p = 0; p < this.selectedElements.length; p++) {
                    o = this.selectedElements[p], 0 != o.dragable && (n = o.selectedLocation.x + k.dx, m = o.selectedLocation.y + k.dy, o.setLocation(n, m), l = e.util.clone(k), l.target = o, o.mousedragHandler(l))
                }
            }
        }, this.mousedragHandler = function (g) {
            var k = this.toSceneEvent(g);
            this.mode == e.SceneMode.normal ? null == this.currentElement || this.currentElement instanceof e.Link ? 1 == this.translate && (this.stage.cursor = e.MouseCursor.closed_hand, this.translateX = this.lastTranslateX + k.dx, this.translateY = this.lastTranslateY + k.dy) : this.dragElements(k) : this.mode == e.SceneMode.drag ? 1 == this.translate && (this.stage.cursor = e.MouseCursor.closed_hand, this.translateX = this.lastTranslateX + k.dx, this.translateY = this.lastTranslateY + k.dy) : this.mode == e.SceneMode.select && (null != this.currentElement ? 1 == this.currentElement.dragable && this.dragElements(k) : 1 == this.areaSelect && this.areaSelectHandle(k)), this.dispatchEvent("mousedrag", k)
        }, this.areaSelectHandle = function (D) {
            var t, s, r, q, C = D.offsetLeft,
                B = D.offsetTop,
                A = this.mouseDownEvent.offsetLeft,
                z = this.mouseDownEvent.offsetTop,
                y = C >= A ? A : C,
                x = B >= z ? z : B,
                w = Math.abs(D.dx) * this.scaleX,
                v = Math.abs(D.dy) * this.scaleY,
                u = new h(y, x, w, v);
            for (i.clearOperations().addOperation(u), C = D.x, B = D.y, A = this.mouseDownEvent.x, z = this.mouseDownEvent.y, y = C >= A ? A : C, x = B >= z ? z : B, w = Math.abs(D.dx), v = Math.abs(D.dy), t = y + w, s = x + v, r = 0; r < i.childs.length; r++) {
                q = i.childs[r], q.x > y && q.x + q.width < t && q.y > x && q.y + q.height < s && i.notInSelectedNodes(q) && (q.selectedHandler(D), i.addToSelected(q))
            }
        }, this.mousemoveHandler = function (g) {
            var l, k;
            return this.mousecoord = {
                x: g.x,
                y: g.y
            }, l = this.toSceneEvent(g), this.mode == e.SceneMode.drag ? (this.stage.cursor = e.MouseCursor.open_hand, void 0) : (this.mode == e.SceneMode.normal ? this.stage.cursor = e.MouseCursor.normal : this.mode == e.SceneMode.select && (this.stage.cursor = e.MouseCursor.normal), k = i.getElementByXY(l.x, l.y), null != k ? (i.mouseOverelement && i.mouseOverelement !== k && (l.target = k, i.mouseOverelement.mouseoutHandler(l)), i.mouseOverelement = k, 0 == k.isMouseOver ? (l.target = k, k.mouseoverHandler(l), i.dispatchEvent("mouseover", l)) : (l.target = k, k.mousemoveHandler(l), i.dispatchEvent("mousemove", l))) : i.mouseOverelement && (l.target = k, i.mouseOverelement.mouseoutHandler(l), i.mouseOverelement = null, i.dispatchEvent("mouseout", l)), i.dispatchEvent("mousemove", l), void 0)
        }, this.mouseoverHandler = function (g) {
            var c = this.toSceneEvent(g);
            this.dispatchEvent("mouseover", c)
        }, this.mouseoutHandler = function (g) {
            var c = this.toSceneEvent(g);
            this.dispatchEvent("mouseout", c)
        }, this.clickHandler = function (g) {
            var c = this.toSceneEvent(g);
            this.currentElement && (c.target = this.currentElement, this.currentElement.clickHandler(c)), this.dispatchEvent("click", c)
        }, this.dbclickHandler = function (g) {
            var c = this.toSceneEvent(g);
            this.currentElement ? (c.target = this.currentElement, this.currentElement.dbclickHandler(c)) : i.cancleAllSelected(), this.dispatchEvent("dbclick", c)
        }, this.mousewheelHandler = function (g) {
            var c = this.toSceneEvent(g);
            this.dispatchEvent("mousewheel", c)
        }, this.touchstart = this.mousedownHander, this.touchmove = this.mousedragHandler, this.touchend = this.mousedownHander, this.keydownHandler = function (c) {
            this.dispatchEvent("keydown", c)
        }, this.keyupHandler = function (c) {
            this.dispatchEvent("keyup", c)
        }, this.addEventListener = function (k, g) {
            var m = this,
                l = function (c) {
                    g.call(m, c)
                };
            return this.messageBus.subscribe(k, l), this
        }, this.removeEventListener = function (c) {
            this.messageBus.unsubscribe(c)
        }, this.removeAllEventListener = function () {
            this.messageBus = new e.util.MessageBus
        }, this.dispatchEvent = function (g, c) {
            return this.messageBus.publish(g, c), this
        }, b = "click,dbclick,mousedown,mouseup,mouseover,mouseout,mousemove,mousedrag,mousewheel,touchstart,touchmove,touchend".split(","), a = this, b.forEach(function (c) {
            a[c] = function (g) {
                null != g ? this.addEventListener(c, g) : this.dispatchEvent(c)
            }
        }), this.zoom = function (g, c) {
            null != g && 0 != g && (this.scaleX = g), null != c && 0 != c && (this.scaleY = c)
        }, this.zoomOut = function (c) {
            0 != c && (null == c && (c = 0.8), this.scaleX /= c, this.scaleY /= c)
        }, this.zoomIn = function (c) {
            0 != c && (null == c && (c = 0.8), this.scaleX *= c, this.scaleY *= c)
        }, this.getBound = function () {
            return {
                left: 0,
                top: 0,
                right: this.stage.canvas.width,
                bottom: this.stage.canvas.height,
                width: this.stage.canvas.width,
                height: this.stage.canvas.height
            }
        }, this.getElementsBound = function () {
            return e.util.getElementsBound(this.childs)
        }, this.translateToCenter = function (k) {
            var g = this.getElementsBound(),
                m = this.stage.canvas.width / 2 - (g.left + g.right) / 2,
                l = this.stage.canvas.height / 2 - (g.top + g.bottom) / 2;
            k && (m = k.canvas.width / 2 - (g.left + g.right) / 2, l = k.canvas.height / 2 - (g.top + g.bottom) / 2), this.translateX = m, this.translateY = l
        }, this.setCenter = function (k, g) {
            var m = k - this.stage.canvas.width / 2,
                l = g - this.stage.canvas.height / 2;
            this.translateX = -m, this.translateY = -l
        }, this.centerAndZoom = function (s, r, q) {
            var p, o, n, m, l, k;
            if (this.translateToCenter(q), null == s || null == r) {
                if (p = this.getElementsBound(), o = p.right - p.left, n = p.bottom - p.top, m = this.stage.canvas.width / o, l = this.stage.canvas.height / n, q && (m = q.canvas.width / o, l = q.canvas.height / n), k = Math.min(m, l), k > 1) {
                    return
                }
                this.zoom(k, k)
            }
            this.zoom(s, r)
        }, this.getCenterLocation = function () {
            return {
                x: i.stage.canvas.width / 2,
                y: i.stage.canvas.height / 2
            }
        }, this.doLayout = function (c) {
            c && c(this, this.childs)
        }, this.toJson = function () {
            var k, g = this,
                c = "{";
            return this.serializedProperties.length, this.serializedProperties.forEach(function (m) {
                var l = g[m];
                "background" == m && (l = g._background.src), "string" == typeof l && (l = '"' + l + '"'), c += '"' + m + '":' + l + ","
            }), c += '"childs":[', k = this.childs.length, this.childs.forEach(function (l, m) {
                c += l.toJson(), k > m + 1 && (c += ",")
            }), c += "]", c += "}"
        }, i
    }
    d.prototype = new e.Element;
    var f = {};
    Object.defineProperties(d.prototype, {
        background: {
            get: function () {
                return this._background
            },
            set: function (b) {
                if ("string" == typeof b) {
                    var c = f[b];
                    null == c && (c = new Image, c.src = b, c.onload = function () {
                        f[b] = c
                    }), this._background = c
                } else {
                    this._background = b
                }
            }
        }
    }), e.Scene = d
}(JTopo),
function (e) {
    function d() {
        this.initialize = function () {
            d.prototype.initialize.apply(this, arguments), this.elementType = "displayElement", this.x = 0, this.y = 0, this.width = 32, this.height = 32, this.visible = !0, this.alpha = 1, this.rotate = 0, this.scaleX = 1, this.scaleY = 1, this.strokeColor = "22,124,255", this.fillColor = "22,124,255", this.shadow = !1, this.shadowBlur = 5, this.shadowColor = "rgba(0,0,0,0.5)", this.shadowOffsetX = 3, this.shadowOffsetY = 6, this.transformAble = !1, this.zIndex = 0;
            var b = "x,y,width,height,visible,alpha,rotate,scaleX,scaleY,strokeColor,fillColor,shadow,shadowColor,shadowOffsetX,shadowOffsetY,transformAble,zIndex".split(",");
            this.serializedProperties = this.serializedProperties.concat(b)
        }, this.initialize(), this.paint = function (b) {
            b.beginPath(), b.fillStyle = "rgba(" + this.fillColor + "," + this.alpha + ")", b.rect(-this.width / 2, -this.height / 2, this.width, this.height), b.fill(), b.stroke(), b.closePath()
        }, this.getLocation = function () {
            return {
                x: this.x,
                y: this.y
            }
        }, this.setLocation = function (g, c) {
            return this.x = g, this.y = c, this
        }, this.getCenterLocation = function () {
            return {
                x: this.x + this.width / 2,
                y: this.y + this.height / 2
            }
        }, this.setCenterLocation = function (g, c) {
            return this.x = g - this.width / 2, this.y = c - this.height / 2, this
        }, this.getSize = function () {
            return {
                width: this.width,
                height: this.heith
            }
        }, this.setSize = function (g, c) {
            return this.width = g, this.height = c, this
        }, this.getBound = function () {
            return {
                left: this.x,
                top: this.y,
                right: this.x + this.width,
                bottom: this.y + this.height,
                width: this.width,
                height: this.height
            }
        }, this.setBound = function (h, g, j, i) {
            return this.setLocation(h, g), this.setSize(j, i), this
        }, this.getDisplayBound = function () {
            return {
                left: this.x,
                top: this.y,
                right: this.x + this.width * this.scaleX,
                bottom: this.y + this.height * this.scaleY
            }
        }, this.getDisplaySize = function () {
            return {
                width: this.width * this.scaleX,
                height: this.height * this.scaleY
            }
        }
    }

    function f() {
        var a, c;
        this.initialize = function () {
            f.prototype.initialize.apply(this, arguments), this.elementType = "interactiveElement", this.dragable = !1, this.selected = !1, this.showSelected = !0, this.selectedLocation = null, this.isMouseOver = !1;
            var b = "dragable,selected,showSelected,isMouseOver".split(",");
            this.serializedProperties = this.serializedProperties.concat(b)
        }, this.initialize(), this.paintSelected = function (b) {
            0 != this.showSelected && (b.save(), b.beginPath(), b.strokeStyle = "rgba(168,202,255, 0.9)", b.fillStyle = "rgba(168,202,236,0.7)", b.rect(-this.width / 2 - 3, -this.height / 2 - 3, this.width + 6, this.height + 6), b.fill(), b.stroke(), b.closePath(), b.restore())
        }, this.paintMouseover = function (b) {
            return this.paintSelected(b)
        }, this.isInBound = function (h, g) {
            return h > this.x && h < this.x + this.width * Math.abs(this.scaleX) && g > this.y && g < this.y + this.height * Math.abs(this.scaleY)
        }, this.selectedHandler = function () {
            this.selected = !0, this.selectedLocation = {
                x: this.x,
                y: this.y
            }
        }, this.unselectedHandler = function () {
            this.selected = !1, this.selectedLocation = null
        }, this.dbclickHandler = function (b) {
            this.dispatchEvent("dbclick", b)
        }, this.clickHandler = function (b) {
            this.dispatchEvent("click", b)
        }, this.mousedownHander = function (b) {
            this.dispatchEvent("mousedown", b)
        }, this.mouseupHandler = function (b) {
            this.dispatchEvent("mouseup", b)
        }, this.mouseoverHandler = function (b) {
            this.isMouseOver = !0, this.dispatchEvent("mouseover", b)
        }, this.mousemoveHandler = function (b) {
            this.dispatchEvent("mousemove", b)
        }, this.mouseoutHandler = function (b) {
            this.isMouseOver = !1, this.dispatchEvent("mouseout", b)
        }, this.mousedragHandler = function (b) {
            this.dispatchEvent("mousedrag", b)
        }, this.addEventListener = function (g, j) {
            var i = this,
                h = function (b) {
                    j.call(i, b)
                };
            return this.messageBus || (this.messageBus = new e.util.MessageBus), this.messageBus.subscribe(g, h), this
        }, this.dispatchEvent = function (h, g) {
            return this.messageBus ? (this.messageBus.publish(h, g), this) : null
        }, this.removeEventListener = function (b) {
            this.messageBus.unsubscribe(b)
        }, this.removeAllEventListener = function () {
            this.messageBus = new e.util.MessageBus
        }, a = "click,dbclick,mousedown,mouseup,mouseover,mouseout,mousemove,mousedrag,touchstart,touchmove,touchend".split(","), c = this, a.forEach(function (b) {
            c[b] = function (g) {
                null != g ? this.addEventListener(b, g) : this.dispatchEvent(b)
            }
        })
    }
    d.prototype = new e.Element, Object.defineProperties(d.prototype, {
        cx: {
            get: function () {
                return this.x + this.width / 2
            },
            set: function (b) {
                this.x = b - this.width / 2
            }
        },
        cy: {
            get: function () {
                return this.y + this.height / 2
            },
            set: function (b) {
                this.y = b - this.height / 2
            }
        }
    }), f.prototype = new d, e.DisplayElement = d, e.InteractiveElement = f
}(JTopo),
function (t) {
    function r(a) {
        this.initialize = function (c) {
            r.prototype.initialize.apply(this, arguments), this.elementType = "node", this.zIndex = t.zIndex_Node, this.text = c, this.font = "12px Consolas", this.fontColor = "255,255,255", this.dragable = !0, this.textPosition = "Bottom_Center", this.textOffsetX = 0, this.textOffsetY = 0, this.transformAble = !0, this.inLinks = null, this.outLinks = null;
            var e = "text,font,fontColor,textPosition,textOffsetX,textOffsetY".split(",");
            this.serializedProperties = this.serializedProperties.concat(e)
        }, this.initialize(a), this.paint = function (b) {
            this.image ? b.drawImage(this.image, -this.width / 2, -this.height / 2, this.width, this.height) : (b.beginPath(), b.fillStyle = "rgba(" + this.fillColor + "," + this.alpha + ")", b.rect(-this.width / 2, -this.height / 2, this.width, this.height), b.fill(), b.closePath()), this.paintText(b)
        }, this.paintText = function (g) {
            var j, i, h, f = this.text;
            null != f && "" != f && (g.beginPath(), g.font = this.font, j = g.measureText(f).width, i = g.measureText("田").width, g.fillStyle = "rgba(" + this.fontColor + ", " + this.alpha + ")", h = this.getTextPostion(this.textPosition, j, i), g.fillText(f, h.x, h.y), g.closePath())
        }, this.getTextPostion = function (f, e, h) {
            var g = null;
            return null == f || "Bottom_Center" == f ? g = {
                x: -this.width / 2 + (this.width - e) / 2,
                y: this.height / 2 + h
            } : "Top_Center" == f ? g = {
                x: -this.width / 2 + (this.width - e) / 2,
                y: -this.height / 2 - h / 2
            } : "Top_Right" == f ? g = {
                x: this.width / 2,
                y: -this.height / 2 - h / 2
            } : "Top_Left" == f ? g = {
                x: -this.width / 2 - e,
                y: -this.height / 2 - h / 2
            } : "Bottom_Right" == f ? g = {
                x: this.width / 2,
                y: this.height / 2 + h
            } : "Bottom_Left" == f ? g = {
                x: -this.width / 2 - e,
                y: this.height / 2 + h
            } : "Middle_Center" == f ? g = {
                x: -this.width / 2 + (this.width - e) / 2,
                y: h / 2
            } : "Middle_Right" == f ? g = {
                x: this.width / 2,
                y: h / 2
            } : "Middle_Left" == f && (g = {
                x: -this.width / 2 - e,
                y: h / 2
            }), null != this.textOffsetX && (g.x += this.textOffsetX), null != this.textOffsetY && (g.y += this.textOffsetY), g
        }, this.setImage = function (i, h) {
            var g, b;
            if (null == i) {
                throw new Error("Node.setImage(): 参数Image对象为空!")
            }
            g = this, "string" == typeof i ? (b = s[i], null == b ? (b = new Image, b.src = i, b.onload = function () {
                s[i] = b, 1 == h && g.setSize(b.width, b.height);
                var c = t.util.genImageAlarm(b);
                c && (b.alarm = c), g.image = b
            }) : (h && this.setSize(b.width, b.height), this.image = b)) : (this.image = i, 1 == h && this.setSize(i.width, i.height))
        }, this.removeHandler = function (d) {
            var c = this;
            this.outLinks && (this.outLinks.forEach(function (b) {
                b.nodeA === c && d.remove(b)
            }), this.outLinks = null), this.inLinks && (this.inLinks.forEach(function (b) {
                b.nodeZ === c && d.remove(b)
            }), this.inLinks = null)
        }
    }

    function q() {
        q.prototype.initialize.apply(this, arguments)
    }

    function p(b) {
        this.initialize(), this.text = b, this.elementType = "TextNode", this.paint = function (c) {
            c.beginPath(), c.font = this.font, this.width = c.measureText(this.text).width, this.height = c.measureText("田").width, c.strokeStyle = "rgba(" + this.fontColor + ", " + this.alpha + ")", c.fillStyle = "rgba(" + this.fontColor + ", " + this.alpha + ")", c.fillText(this.text, -this.width / 2, this.height / 2), c.closePath()
        }, this.paintSelected = function (c) {
            c.save(), c.beginPath(), c.font = this.font, c.strokeStyle = "rgba(168,202,255, 0.9)", c.fillStyle = "rgba(168,202,236,0.7)", c.rect(-this.width / 2 - 3, -this.height / 2 - 3, this.width + 6, this.height + 6), c.fill(), c.stroke(), c.closePath(), c.restore()
        }
    }

    function o(e, d, f) {
        this.initialize(), this.text = e, this.href = d, this.target = f, this.elementType = "LinkNode", this.isVisited = !1, this.visitedColor = null, this.paint = function (b) {
            b.beginPath(), b.font = this.font, this.width = b.measureText(this.text).width, this.height = b.measureText("田").width, this.isVisited && null != this.visitedColor ? (b.strokeStyle = "rgba(" + this.visitedColor + ", " + this.alpha + ")", b.fillStyle = "rgba(" + this.visitedColor + ", " + this.alpha + ")") : (b.strokeStyle = "rgba(" + this.fontColor + ", " + this.alpha + ")", b.fillStyle = "rgba(" + this.fontColor + ", " + this.alpha + ")"), b.fillText(this.text, -this.width / 2, this.height / 2), this.isMouseOver && (b.moveTo(-this.width / 2, this.height), b.lineTo(this.width / 2, this.height), b.stroke()), b.closePath()
        }, this.paintSelected = function () {}, this.mousemove(function () {
            var c, g = document.getElementsByTagName("canvas");
            if (g && g.length > 0) {
                for (c = 0; c < g.length; c++) {
                    g[c].style.cursor = "pointer"
                }
            }
        }), this.mouseout(function () {
            var c, g = document.getElementsByTagName("canvas");
            if (g && g.length > 0) {
                for (c = 0; c < g.length; c++) {
                    g[c].style.cursor = "default"
                }
            }
        }), this.click(function () {
            "_blank" == this.target ? window.open(this.href) : location = this.href, this.isVisited = !0
        })
    }

    function n(b) {
        this.initialize(arguments), this._radius = 20, this.beginDegree = 0, this.endDegree = 2 * Math.PI, this.text = b, this.paint = function (c) {
            c.save(), c.beginPath(), c.fillStyle = "rgba(" + this.fillColor + "," + this.alpha + ")", c.arc(0, 0, this.radius, this.beginDegree, this.endDegree, !0), c.fill(), c.closePath(), c.restore(), this.paintText(c)
        }, this.paintSelected = function (c) {
            c.save(), c.beginPath(), c.strokeStyle = "rgba(168,202,255, 0.9)", c.fillStyle = "rgba(168,202,236,0.7)", c.arc(0, 0, this.radius + 3, this.beginDegree, this.endDegree, !0), c.fill(), c.stroke(), c.closePath(), c.restore()
        }
    }

    function m(g, f, j) {
        var i, h;
        this.initialize(), this.frameImages = g || [], this.frameIndex = 0, this.isStop = !0, i = f || 1000, this.repeatPlay = !1, h = this, this.nextFrame = function () {
            if (!this.isStop && null != this.frameImages.length) {
                if (this.frameIndex++, this.frameIndex >= this.frameImages.length) {
                    if (!this.repeatPlay) {
                        return
                    }
                    this.frameIndex = 0
                }
                this.setImage(this.frameImages[this.frameIndex], j), setTimeout(function () {
                    h.nextFrame()
                }, i / g.length)
            }
        }
    }

    function l(i, h, x, w, v) {
        var u, j;
        this.initialize(), u = this, this.setImage(i), this.frameIndex = 0, this.isPause = !0, this.repeatPlay = !1, j = w || 1000, v = v || 0, this.paint = function (e) {
            var c, A, z, y;
            this.image && (c = this.width, A = this.height, e.save(), e.beginPath(), e.fillStyle = "rgba(" + this.fillColor + "," + this.alpha + ")", z = (Math.floor(this.frameIndex / x) + v) * A, y = Math.floor(this.frameIndex % x) * c, e.drawImage(this.image, y, z, c, A, -c / 2, -A / 2, c, A), e.fill(), e.closePath(), e.restore())
        }, this.nextFrame = function () {
            if (!this.isStop) {
                if (this.frameIndex++, this.frameIndex >= h * x) {
                    if (!this.repeatPlay) {
                        return
                    }
                    this.frameIndex = 0
                }
                setTimeout(function () {
                    u.isStop || u.nextFrame()
                }, j / (h * x))
            }
        }
    }

    function k() {
        var b = null;
        return b = arguments.length <= 3 ? new m(arguments[0], arguments[1], arguments[2]) : new l(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4], arguments[5]), b.stop = function () {
            b.isStop = !0
        }, b.play = function () {
            b.isStop = !1, b.frameIndex = 0, b.nextFrame()
        }, b
    }
    var s = {};
    r.prototype = new t.InteractiveElement, q.prototype = new r, p.prototype = new q, o.prototype = new p, n.prototype = new q, Object.defineProperties(n.prototype, {
        radius: {
            get: function () {
                return this._radius
            },
            set: function (e) {
                var d, f;
                this._radius = e, d = 2 * this.radius, f = 2 * this.radius, this.width = d, this.height = f
            }
        }
    }), m.prototype = new q, l.prototype = new q, k.prototype = new q, t.Node = q, t.TextNode = p, t.LinkNode = o, t.CircleNode = n, t.AnimateNode = k
}(JTopo),
function (f) {
    function e(k, j, i) {
        function b(m, l) {
            var q, p, o, n, r = [];
            if (null == m || null == l) {
                return r
            }
            if (m && l && m.outLinks && l.inLinks) {
                for (q = 0; q < m.outLinks.length; q++) {
                    for (p = m.outLinks[q], o = 0; o < l.inLinks.length; o++) {
                        n = l.inLinks[o], p === n && r.push(n)
                    }
                }
            }
            return r
        }
        this.initialize = function (o, n, m) {
            if (e.prototype.initialize.apply(this, arguments), this.elementType = "link", this.zIndex = f.zIndex_Link, 0 != arguments.length) {
                this.text = m, this.nodeA = o, this.nodeZ = n, this.nodeA && null == this.nodeA.outLinks && (this.nodeA.outLinks = []), this.nodeA && null == this.nodeA.inLinks && (this.nodeA.inLinks = []), this.nodeZ && null == this.nodeZ.inLinks && (this.nodeZ.inLinks = []), this.nodeZ && null == this.nodeZ.outLinks && (this.nodeZ.outLinks = []), null != this.nodeA && this.nodeA.outLinks.push(this), null != this.nodeZ && this.nodeZ.inLinks.push(this), this.caculateIndex(), this.font = "12px Consolas", this.fontColor = "255,255,255", this.lineWidth = 2, this.lineJoin = "miter", this.transformAble = !1, this.bundleOffset = 20, this.bundleGap = 12, this.textOffsetX = 0, this.textOffsetY = 0, this.arrowsRadius = null, this.arrowsOffset = 0, null != this.nodeZ && (this.arrowsOffset = -Math.max(this.nodeZ.width, this.nodeZ.height) / 2), this.path = [];
                var l = "text,font,fontColor,lineWidth,lineJoin".split(",");
                this.serializedProperties = this.serializedProperties.concat(l)
            }
        }, this.caculateIndex = function () {
            var d = this,
                c = this.getLinksCount();
            c > 0 && (d.nodeIndex = c - 1)
        }, this.getAllLinks = function () {
            var l = b(this.nodeA, this.nodeZ),
                n = b(this.nodeZ, this.nodeA),
                m = l.concat(n);
            return m
        }, this.getBrotherLinks = function () {
            var d = this,
                c = this.getAllLinks();
            return c = c.filter(function (l) {
                return l !== d
            })
        }, this.getLinksCount = function () {
            return this.getAllLinks().length
        }, this.initialize(k, j, i), this.removeHandler = function () {
            var c, d = this;
            this.nodeA && this.nodeA.outLinks && (this.nodeA.outLinks = this.nodeA.outLinks.filter(function (l) {
                return l !== d
            })), this.nodeZ && this.nodeZ.inLinks && (this.nodeZ.inLinks = this.nodeZ.inLinks.filter(function (l) {
                return l !== d
            })), c = this.getBrotherLinks(), c.forEach(function (m, l) {
                m.nodeIndex = l
            })
        }, this.getPath = function () {
            var z, y, x, w, v, u, t, s, r, q, C = [],
                B = {
                    x: this.nodeA.cx,
                    y: this.nodeA.cy
                },
                A = {
                    x: this.nodeZ.cx,
                    y: this.nodeZ.cy
                };
            return this.nodeA === this.nodeZ ? [B, A] : (this.nodeA, z = this.getLinksCount(), y = Math.atan2(A.y - B.y, A.x - B.x), x = {
                x: B.x + this.bundleOffset * Math.cos(y),
                y: B.y + this.bundleOffset * Math.sin(y)
            }, w = {
                x: A.x + this.bundleOffset * Math.cos(y - Math.PI),
                y: A.y + this.bundleOffset * Math.sin(y - Math.PI)
            }, v = y - Math.PI / 2, u = y - Math.PI / 2, t = z * this.bundleGap / 2 - this.bundleGap / 2, s = this.bundleGap * this.nodeIndex, r = {
                x: x.x + s * Math.cos(v),
                y: x.y + s * Math.sin(v)
            }, q = {
                x: w.x + s * Math.cos(u),
                y: w.y + s * Math.sin(u)
            }, r = {
                x: r.x + t * Math.cos(v - Math.PI),
                y: r.y + t * Math.sin(v - Math.PI)
            }, q = {
                x: q.x + t * Math.cos(u - Math.PI),
                y: q.y + t * Math.sin(u - Math.PI)
            }, C.push({
                x: B.x,
                y: B.y
            }), C.push({
                x: r.x,
                y: r.y
            }), C.push({
                x: q.x,
                y: q.y
            }), C.push({
                x: A.x,
                y: A.y
            }), C)
        }, this.paintPath = function (m, l) {
            var p, o, n;
            if (this.nodeA === this.nodeZ) {
                return this.paintLoop(m), void 0
            }
            for (m.beginPath(), m.moveTo(l[0].x, l[0].y), p = 1; p < l.length; p++) {
                m.lineTo(l[p].x, l[p].y)
            }
            m.stroke(), m.closePath(), null != this.arrowsRadius && (o = l[l.length - 2], n = l[l.length - 1], this.paintArrow(m, o, n, this.arrowsOffset))
        }, this.paintLoop = function (d) {
            d.beginPath();
            var c = this.bundleGap * (this.nodeIndex + 1) / 2;
            Math.PI + Math.PI / 2, d.arc(this.nodeA.x, this.nodeA.y, c, Math.PI / 2, 2 * Math.PI), d.stroke(), d.closePath()
        }, this.paintArrow = function (E, D, C, B) {
            var r, q, A = this.arrowsRadius / 2,
                z = D,
                y = C,
                x = Math.atan2(y.y - z.y, y.x - z.x),
                w = f.util.getDistance(z, y) - this.arrowsRadius,
                v = z.x + (w + B) * Math.cos(x),
                u = z.y + (w + B) * Math.sin(x),
                t = y.x + B * Math.cos(x),
                s = y.y + B * Math.sin(x);
            x -= Math.PI / 2, r = {
                x: v + A * Math.cos(x),
                y: u + A * Math.sin(x)
            }, q = {
                x: v + A * Math.cos(x - Math.PI),
                y: u + A * Math.sin(x - Math.PI)
            }, E.beginPath(), E.fillStyle = "rgba(" + this.strokeColor + "," + this.alpha + ")", E.moveTo(r.x, r.y), E.lineTo(t, s), E.lineTo(q.x, q.y), E.stroke(), E.closePath()
        }, this.paint = function (d) {
            if (null != this.nodeA && null != !this.nodeZ) {
                var c = this.getPath(this.nodeIndex);
                this.path = c, d.strokeStyle = "rgba(" + this.strokeColor + "," + this.alpha + ")", d.lineWidth = this.lineWidth, this.paintPath(d, c), c && c.length > 0 && this.paintText(d, c)
            }
        };
        var a = -(Math.PI / 2 + Math.PI / 4);
        this.paintText = function (t, s) {
            var p, o, n, m, l, r = s[0],
                q = s[s.length - 1];
            4 == s.length && (r = s[1], q = s[2]), this.text && this.text.length > 0 && (p = (q.x + r.x) / 2 + this.textOffsetX, o = (q.y + r.y) / 2 + this.textOffsetY, t.save(), t.beginPath(), t.font = this.font, n = t.measureText(this.text).width, m = t.measureText("田").width, t.fillStyle = "rgba(" + this.fontColor + ", " + this.alpha + ")", this.nodeA === this.nodeZ ? (l = this.bundleGap * (this.nodeIndex + 1) / 2, p = this.nodeA.x + l * Math.cos(a), o = this.nodeA.y + l * Math.sin(a), t.fillText(this.text, p, o)) : t.fillText(this.text, p - n / 2, o - m / 2), t.stroke(), t.closePath(), t.restore())
        }, this.paintSelected = function (c) {
            c.shadowBlur = 10, c.shadowColor = "rgba(0,0,0,1)", c.shadowOffsetX = 0, c.shadowOffsetY = 0
        }, this.isInBound = function (l, s) {
            var r, q, p, o, n, m;
            if (this.nodeA === this.nodeZ) {
                return r = this.bundleGap * (this.nodeIndex + 1) / 2, q = f.util.getDistance(this.nodeA, {
                    x: l,
                    y: s
                }) - r, Math.abs(q) <= 3
            }
            for (p = !1, o = 1; o < this.path.length; o++) {
                if (n = this.path[o - 1], m = this.path[o], 1 == f.util.isPointInLine({
                    x: l,
                    y: s
                }, n, m)) {
                    p = !0;
                    break
                }
            }
            return p
        }
    }

    function h(i, c, j) {
        this.initialize = function () {
            h.prototype.initialize.apply(this, arguments), this.direction = "horizontal"
        }, this.initialize(i, c, j), this.getPath = function (s) {
            var o, n, m, l, k, r = [],
                q = {
                    x: this.nodeA.cx,
                    y: this.nodeA.cy
                },
                p = {
                    x: this.nodeZ.cx,
                    y: this.nodeZ.cy
                };
            return this.nodeA === this.nodeZ ? [q, p] : (o = this.getLinksCount(), n = (o - 1) * this.bundleGap, m = this.bundleGap * s - n / 2, "vertical" == this.direction ? (l = q.x + m, k = p.y - m, r.push({
                x: l,
                y: q.y
            }), r.push({
                x: l,
                y: k
            }), r.push({
                x: p.x,
                y: k
            })) : (l = p.x + m, k = q.y - m, r.push({
                x: q.x,
                y: k
            }), r.push({
                x: l,
                y: k
            }), r.push({
                x: l,
                y: p.y
            })), r)
        }, this.paintText = function (l, k) {
            var q, p, o, n, m;
            this.text && this.text.length > 0 && (q = k[1], p = q.x + this.textOffsetX, o = q.y + this.textOffsetY, l.save(), l.beginPath(), l.font = this.font, n = l.measureText(this.text).width, m = l.measureText("田").width, l.fillStyle = "rgba(" + this.fontColor + ", " + this.alpha + ")", l.fillText(this.text, p - n / 2, o - m / 2), l.stroke(), l.closePath(), l.restore())
        }
    }

    function g(i, d, j) {
        this.initialize = function () {
            g.prototype.initialize.apply(this, arguments), this.direction = "vertical", this.offsetGap = 44
        }, this.initialize(i, d, j), this.getPath = function (l) {
            var k, r, q, p, o, n, m;
            return this.nodeA === this.nodeZ ? [r, q] : (k = [], r = {
                x: this.nodeA.cx,
                y: this.nodeA.cy
            }, q = {
                x: this.nodeZ.cx,
                y: this.nodeZ.cy
            }, p = this.getLinksCount(), o = (p - 1) * this.bundleGap, n = this.bundleGap * l - o / 2, m = this.offsetGap, "vertical" == this.direction ? (r.y > q.y && (m = -m), k.push({
                x: r.x + n,
                y: r.y
            }), k.push({
                x: r.x + n,
                y: r.y + m
            }), k.push({
                x: q.x + n,
                y: q.y - m
            }), k.push({
                x: q.x + n,
                y: q.y
            })) : (r.x > q.x && (m = -m), k.push({
                x: r.x,
                y: r.y + n
            }), k.push({
                x: r.x + m,
                y: r.y + n
            }), k.push({
                x: q.x - m,
                y: q.y + n
            }), k.push({
                x: q.x,
                y: q.y + n
            })), k)
        }
    }
    e.prototype = new f.InteractiveElement, h.prototype = new e, g.prototype = new e, f.Link = e, f.FoldLink = h, f.FlexionalLink = g
}(JTopo),
function (d) {
    function c(a) {
        this.initialize = function (b) {
            c.prototype.initialize.apply(this, null), this.elementType = "container", this.zIndex = d.zIndex_Container, this.width = 100, this.height = 100, this.childs = [], this.alpha = 0.5, this.dragable = !0, this.childDragble = !0, this.visible = !0, this.fillColor = "10,100,80", this.borderColor = "0,255,0", this.font = "12px Consolas", this.fontColor = "255,255,255", this.text = b, this.textPosition = "Bottom_Center", this.textOffsetX = 0, this.textOffsetY = 0, this.layout = new d.layout.AutoBoundLayout
        }, this.initialize(a), this.add = function (b) {
            this.childs.push(b), b.dragable = this.childDragble
        }, this.remove = function (f) {
            for (var e = 0; e < this.childs.length; e++) {
                if (this.childs[e] === f) {
                    f.parentContainer = null, this.childs = this.childs.del(e), f.lastParentContainer = this;
                    break
                }
            }
        }, this.removeAll = function () {
            this.childs = []
        }, this.setLocation = function (h, g) {
            var j, i, l = h - this.x,
                k = g - this.y;
            for (this.x = h, this.y = g, j = 0; j < this.childs.length; j++) {
                i = this.childs[j], i.setLocation(i.x + l, i.y + k)
            }
        }, this.doLayout = function (b) {
            b && b(this, this.childs)
        }, this.paint = function (b) {
            this.visible && (this.layout && this.layout(this, this.childs), b.beginPath(), b.shadowBlur = 9, b.shadowColor = "rgba(0,0,0,0.5)", b.shadowOffsetX = 3, b.shadowOffsetY = 3, b.strokeStyle = "rgba(" + this.borderColor + "," + this.alpha + ")", b.fillStyle = "rgba(" + this.fillColor + "," + this.alpha + ")", b.rect(this.x, this.y, this.width, this.height), b.fill(), b.stroke(), b.closePath(), this.paintText(b))
        }, this.paintText = function (g) {
            var j, i, h, f = this.text;
            null != f && "" != f && (g.beginPath(), g.font = this.font, j = g.measureText(f).width, i = g.measureText("田").width, g.fillStyle = "rgba(" + this.fontColor + ", " + this.alpha + ")", h = this.getTextPostion(this.textPosition, j, i), g.fillText(f, h.x, h.y), g.closePath())
        }, this.getTextPostion = function (f, e, h) {
            var g = null;
            return null == f || "Bottom_Center" == f ? g = {
                x: this.x + this.width / 2 - e / 2,
                y: this.y + this.height + h
            } : "Top_Center" == f ? g = {
                x: this.x + this.width / 2 - e / 2,
                y: this.y - h / 2
            } : "Top_Right" == f ? g = {
                x: this.x + this.width - e,
                y: this.y - h / 2
            } : "Top_Left" == f ? g = {
                x: this.x,
                y: this.y - h / 2
            } : "Bottom_Right" == f ? g = {
                x: this.x + this.width - e,
                y: this.y + this.height + h
            } : "Bottom_Left" == f ? g = {
                x: this.x,
                y: this.y + this.height + h
            } : "Middle_Center" == f ? g = {
                x: this.x + this.width / 2 - e / 2,
                y: this.y + this.height / 2 + h / 2
            } : "Middle_Right" == f ? g = {
                x: this.x + this.width - e,
                y: this.y + this.height / 2 + h / 2
            } : "Middle_Left" == f && (g = {
                x: this.x,
                y: this.y + this.height / 2 + h / 2
            }), null != this.textOffsetX && (g.x += this.textOffsetX), null != this.textOffsetY && (g.y += this.textOffsetY), g
        }, this.paintSelected = function () {}
    }
    c.prototype = new d.InteractiveElement, d.Container = c
}(JTopo),
function (L) {
    function K(d, c) {
        return function (P) {
            var N, M, t, s, r, q, p, i, b, a, O = P.childs;
            if (!(O.length <= 0)) {
                for (N = P.getBound(), M = O[0], t = (N.width - M.width) / c, s = (N.height - M.height) / d, O.length, r = 0, q = 0; d > q; q++) {
                    for (p = 0; c > p; p++) {
                        if (i = O[r++], b = N.left + t / 2 + p * t, a = N.top + s / 2 + q * s, i.setLocation(b, a), r >= O.length) {
                            return
                        }
                    }
                }
            }
        }
    }

    function J(d, c) {
        return null == d && (d = 0), null == c && (c = 0),
            function (o) {
                var l, k, j, b, a, m = o.childs;
                if (!(m.length <= 0)) {
                    for (l = o.getBound(), k = l.left, j = l.top, b = 0; b < m.length; b++) {
                        a = m[b], k + a.width >= l.right && (k = l.left, j += c + a.height), a.setLocation(k, j), k += d + a.width
                    }
                }
            }
    }

    function I() {
        return function (M, t) {
            var s, r, q, p, o, m, l, k;
            if (t.length > 0) {
                for (s = 10000000, r = -10000000, q = 10000000, p = -10000000, o = r - s, m = p - q, l = 0; l < t.length; l++) {
                    k = t[l], k.x <= s && (s = k.x), k.x >= r && (r = k.x), k.y <= q && (q = k.y), k.y >= p && (p = k.y), o = r - s + k.width, m = p - q + k.height
                }
                M.x = s, M.y = q, M.width = o, M.height = m
            }
        }
    }

    function H(f, e, h, g) {
        return null == f && (f = 0), null == e && (e = f), null == h && (h = 0, g = 2 * Math.PI),
            function (N) {
                var t, s, r, q, p, d, c, b, a, M = N.childs;
                if (!(M.length <= 0)) {
                    for (t = N.getBound(), s = t.left + t.width / 2, r = t.top + t.height / 2, q = h, p = (g - h) / M.length, d = 0; d < M.length; d++) {
                        c = M[d], b = s + Math.cos(q) * f, a = r + Math.sin(q) * e, c.setLocation(b, a), q += p
                    }
                }
            }
    }

    function G(a) {
        var f = [],
            e = a.filter(function (c) {
                return c instanceof L.Link ? !0 : (f.push(c), !1)
            });
        return a = f.filter(function (d) {
            for (var c = 0; c < e.length; c++) {
                if (e[c].nodeZ === d) {
                    return !1
                }
            }
            return !0
        }), a = a.filter(function (d) {
            for (var c = 0; c < e.length; c++) {
                if (e[c].nodeA === d) {
                    return !0
                }
            }
            return !1
        })
    }

    function F(e) {
        var d = 0,
            f = 0;
        return e.forEach(function (b) {
            d += b.width, f += b.height
        }), {
            width: d / e.length,
            height: f / e.length
        }
    }

    function E(h, g, l, k) {
        var j, i;
        for (g.x += l, g.y += k, j = w(h, g), i = 0; i < j.length; i++) {
            E(h, j[i], l, k)
        }
    }

    function D(f, e) {
        function g(a, i) {
            var c, d = w(f, a);
            for (null == h[i] && (h[i] = {}, h[i].nodes = [], h[i].childs = []), h[i].nodes.push(a), h[i].childs.push(d), c = 0; c < d.length; c++) {
                g(d[c], i + 1), d[c].parent = a
            }
        }
        var h = [];
        return g(e, 0), h
    }

    function C(a, f, e) {
        return function (i) {
            function g(ae, ad) {
                var Z, Y, X, W, V, U, T, S, R, Q, P, O, N, M, ac = L.layout.getTreeDeep(ae, ad),
                    ab = D(ae, ad),
                    aa = ab["" + ac].nodes;
                for (Z = 0; Z < aa.length; Z++) {
                    Y = aa[Z], X = (Z + 1) * (f + 10), W = ac * e, "down" == a || ("up" == a ? W = -W : "left" == a ? (X = -ac * e, W = (Z + 1) * (f + 10)) : "right" == a && (X = ac * e, W = (Z + 1) * (f + 10))), Y.setLocation(X, W)
                }
                for (V = ac - 1; V >= 0; V--) {
                    for (U = ab["" + V].nodes, T = ab["" + V].childs, Z = 0; Z < U.length; Z++) {
                        if (S = U[Z], R = T[Z], "down" == a ? S.y = V * e : "up" == a ? S.y = -V * e : "left" == a ? S.x = -V * e : "right" == a && (S.x = V * e), R.length > 0 ? "down" == a || "up" == a ? S.x = (R[0].x + R[R.length - 1].x) / 2 : ("left" == a || "right" == a) && (S.y = (R[0].y + R[R.length - 1].y) / 2) : Z > 0 && ("down" == a || "up" == a ? S.x = U[Z - 1].x + U[Z - 1].width + f : ("left" == a || "right" == a) && (S.y = U[Z - 1].y + U[Z - 1].height + f)), Z > 0) {
                            if ("down" == a || "up" == a) {
                                if (S.x < U[Z - 1].x + U[Z - 1].width) {
                                    for (Q = U[Z - 1].x + U[Z - 1].width + f, P = Math.abs(Q - S.x), O = Z; O < U.length; O++) {
                                        E(i.childs, U[O], P, 0)
                                    }
                                }
                            } else {
                                if (("left" == a || "right" == a) && S.y < U[Z - 1].y + U[Z - 1].height) {
                                    for (N = U[Z - 1].y + U[Z - 1].height + f, M = Math.abs(N - S.y), O = Z; O < U.length; O++) {
                                        E(i.childs, U[O], 0, M)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            var d, c, b, q, p, h = null;
            null == f && (h = F(i.childs), f = h.width, ("left" == a || "right" == a) && (f = h.width + 10)), null == e && (null == h && (h = F(i.childs)), e = 2 * h.height), null == a && (a = "down"), d = L.layout.getRootNodes(i.childs), d.length > 0 && (g(i.childs, d[0]), c = L.util.getElementsBound(i.childs), b = i.getCenterLocation(), q = b.x - (c.left + c.right) / 2, p = b.y - (c.top + c.bottom) / 2, i.childs.forEach(function (j) {
                j instanceof L.Node && (j.x += q, j.y += p)
            }))
        }
    }

    function H() {
        return function (a) {
            function o(g, c, q) {
                var h, p = w(g, c);
                0 != p.length && (null == q && (q = 200), h = 2 * Math.PI / p.length, p.forEach(function (r, f) {
                    var d = c.x + q * Math.cos(h * f),
                        b = c.y + q * Math.sin(h * f);
                    r.setLocation(d, b), o(g, r, q / 2)
                }))
            }
            var l, k, j, i, m = L.layout.getRootNodes(a.childs);
            m.length > 0 && (o(a.childs, m[0]), l = L.util.getElementsBound(a.childs), k = a.getCenterLocation(), j = k.x - (l.left + l.right) / 2, i = k.y - (l.top + l.bottom) / 2, a.childs.forEach(function (c) {
                c instanceof L.Node && (c.x += j, c.y += i)
            }))
        }
    }

    function B(s, r, q, p, o, m) {
        var k, j, l = [];
        for (k = 0; q > k; k++) {
            for (j = 0; p > j; j++) {
                l.push({
                    x: s + j * o,
                    y: r + k * m
                })
            }
        }
        return l
    }

    function A(T, S, R, Q, P, O) {
        var q, p, o, N = P ? P : 0,
            M = O ? O : 2 * Math.PI,
            t = M - N,
            s = t / R,
            r = [];
        for (N += s / 2, q = N; M >= q; q += s) {
            p = T + Math.cos(q) * Q, o = S + Math.sin(q) * Q, r.push({
                x: p,
                y: o
            })
        }
        return r
    }

    function z(M, t, s, r, q, p) {
        var l, k, o = p || "bottom",
            m = [];
        if ("bottom" == o) {
            for (l = M - s / 2 * r + r / 2, k = 0; s >= k; k++) {
                m.push({
                    x: l + k * r,
                    y: t + q
                })
            }
        } else {
            if ("top" == o) {
                for (l = M - s / 2 * r + r / 2, k = 0; s >= k; k++) {
                    m.push({
                        x: l + k * r,
                        y: t - q
                    })
                }
            } else {
                if ("right" == o) {
                    for (l = t - s / 2 * r + r / 2, k = 0; s >= k; k++) {
                        m.push({
                            x: M + q,
                            y: l + k * r
                        })
                    }
                } else {
                    if ("left" == o) {
                        for (l = t - s / 2 * r + r / 2, k = 0; s >= k; k++) {
                            m.push({
                                x: M - q,
                                y: l + k * r
                            })
                        }
                    }
                }
            }
        }
        return m
    }

    function B(s, r, q, p, o, m) {
        var k, j, l = [];
        for (k = 0; q > k; k++) {
            for (j = 0; p > j; j++) {
                l.push({
                    x: s + j * o,
                    y: r + k * m
                })
            }
        }
        return l
    }

    function y(i, h) {
        var o, m, l, k, j;
        if (i.layout) {
            if (o = i.layout, m = o.type, l = null, k = i.getCenterLocation(), "star" == m) {
                l = A(k.x, k.y, h.length, i.layout.radius, i.layout.beginDegree, i.layout.endDegree)
            } else {
                if ("tree" == m) {
                    l = z(k.x, k.y, h.length, o.width, o.height, o.direction)
                } else {
                    if ("grid" != m) {
                        return
                    }
                    l = B(i.x, i.y, o.rows, o.cols, o.horizontal || 0, o.vertical || 0)
                }
            }
            for (j = 0; j < h.length; j++) {
                h[j].setCenterLocation(l[j].x, l[j].y)
            }
        }
    }

    function x(f, e) {
        var h, g;
        for (h = 0; h < e.length; h++) {
            if (g = w(f, e[h]), g.length > 0) {
                return !1
            }
        }
        return !0
    }

    function w(a, h) {
        var f, g = [];
        for (f = 0; f < a.length; f++) {
            a[f] instanceof L.Link && a[f].nodeA === h && g.push(a[f].nodeZ)
        }
        return g
    }

    function v(f, e) {
        var g, h = w(f, e);
        if (0 == h.length) {
            return null
        }
        if (y(e, h), x(f, h)) {
            return null
        }
        for (g = 0; g < h.length; g++) {
            v(f, h[g])
        }
        return null
    }

    function u(O, N) {
        function p(d, c) {
            var f = d.x - c.x,
                e = d.y - c.y;
            r += f * M, q += e * M, r *= t, q *= t, q += s, c.x += r, c.y += q
        }

        function a() {
            if (!(++o > 150)) {
                for (var b = 0; b < m.length; b++) {
                    m[b] != O && p(O, m[b], m)
                }
                setTimeout(a, 1000 / 24)
            }
        }
        var M = 0.01,
            t = 0.95,
            s = -5,
            r = 0,
            q = 0,
            o = 0,
            m = N.getElementsByClass(L.Node);
        a()
    }

    function n(f, e) {
        function g(d, c, k) {
            var i, j = w(d, c);
            for (k > h && (h = k), i = 0; i < j.length; i++) {
                g(d, j[i], k + 1)
            }
        }
        var h = 0;
        return g(f, e, 0), h
    }
    L.layout = L.Layout = {
        layoutNode: v,
        getNodeChilds: w,
        adjustPosition: y,
        springLayout: u,
        getTreeDeep: n,
        getRootNodes: G,
        GridLayout: K,
        FlowLayout: J,
        AutoBoundLayout: I,
        CircleLayout: H,
        TreeLayout: C
    }
}(JTopo),
function (e) {
    function d() {
        var a = new e.CircleNode;
        return a.radius = 150, a.colors = ["#3666B0", "#2CA8E0", "#77D1F6"], a.datas = [0.3, 0.3, 0.4], a.titles = ["A", "B", "C"], a.paint = function (v) {
            var s, r, q, p, o, n, j, b, u = 2 * a.radius,
                t = 2 * a.radius;
            for (a.width = u, a.height = t, s = 0, r = 0; r < this.datas.length; r++) {
                q = 2 * this.datas[r] * Math.PI, v.save(), v.beginPath(), v.fillStyle = a.colors[r], v.moveTo(0, 0), v.arc(0, 0, this.radius, s, s + q, !1), v.fill(), v.closePath(), v.restore(), v.beginPath(), v.font = this.font, p = this.titles[r] + ": " + (100 * this.datas[r]).toFixed(2) + "%", o = v.measureText(p).width, v.measureText("田").width, n = (s + s + q) / 2, j = this.radius * Math.cos(n), b = this.radius * Math.sin(n), n > Math.PI / 2 && n <= Math.PI ? j -= o : n > Math.PI && n < 3 * 2 * Math.PI / 4 ? j -= o : n > 0.75 * 2 * Math.PI, v.fillStyle = "#FFFFFF", v.fillText(p, j, b), v.moveTo(this.radius * Math.cos(n), this.radius * Math.sin(n)), n > Math.PI / 2 && n < 3 * 2 * Math.PI / 4 && (j -= o), n > Math.PI, v.fill(), v.stroke(), v.closePath(), s += q
            }
        }, a
    }

    function f() {
        var a = new e.Node;
        return a.showSelected = !1, a.width = 250, a.height = 180, a.colors = ["#3666B0", "#2CA8E0", "#77D1F6"], a.datas = [0.3, 0.3, 0.4], a.titles = ["A", "B", "C"], a.paint = function (t) {
            var q, p, o, n, m, c, b, s = 3,
                r = (this.width - s) / this.datas.length;
            for (t.save(), t.beginPath(), t.fillStyle = "#FFFFFF", t.strokeStyle = "#FFFFFF", t.moveTo(-this.width / 2 - 1, -this.height / 2), t.lineTo(-this.width / 2 - 1, this.height / 2 + 3), t.lineTo(this.width / 2 + s + 1, this.height / 2 + 3), t.stroke(), t.closePath(), t.restore(), q = 0; q < this.datas.length; q++) {
                t.save(), t.beginPath(), t.fillStyle = a.colors[q], p = this.datas[q], o = q * (r + s) - this.width / 2, n = this.height - p - this.height / 2, t.fillRect(o, n, r, p), m = "" + parseInt(this.datas[q]), c = t.measureText(m).width, b = t.measureText("田").width, t.fillStyle = "#FFFFFF", t.fillText(m, o + (r - c) / 2, n - b), t.fillText(this.titles[q], o + (r - c) / 2, this.height / 2 + b), t.fill(), t.closePath(), t.restore()
            }
        }, a
    }
    e.BarChartNode = f, e.PieChartNode = d
}(JTopo),
function (D) {
    function C(a, i) {
        var f, h = null;
        return {
            stop: function () {
                return f ? (window.clearInterval(f), h && h.publish("stop"), this) : this
            },
            start: function () {
                var b = this;
                return f = setInterval(function () {
                    a.call(b)
                }, i), this
            },
            onStop: function (c) {
                return null == h && (h = new D.util.MessageBus), h.subscribe("stop", c), this
            }
        }
    }

    function B(b, p) {
        var o, n, m, l, k, j;
        return p = p || {}, o = p.gravity || 0.1, n = p.dx || 0, m = p.dy || 5, l = p.stop, k = p.interval || 30, j = new C(function () {
            l && l() ? (m = 0.5, this.stop()) : (m += o, b.setLocation(b.x + n, b.y + m))
        }, k)
    }

    function A(I, H, G, F, E) {
        var o, n, m, b, q = 1000 / 24,
            p = {};
        for (o in H) {
            n = H[o], m = n - I[o], p[o] = {
                oldValue: I[o],
                targetValue: n,
                step: m / G * q,
                isDone: function (a) {
                    var d = this.step > 0 && I[a] >= this.targetValue || this.step < 0 && I[a] <= this.targetValue;
                    return d
                }
            }
        }
        return b = new C(function () {
            var f, c, a = !0;
            for (f in H) {
                p[f].isDone(f) || (I[f] += p[f].step, a = !1)
            }
            if (a) {
                if (!F) {
                    return this.stop()
                }
                for (f in H) {
                    E ? (c = p[f].targetValue, p[f].targetValue = p[f].oldValue, p[f].oldValue = c, p[f].step = -p[f].step) : I[f] = p[f].oldValue
                }
            }
            return this
        }, q)
    }

    function z(i) {
        var h, l, k, j;
        return null == i && (i = {}), h = i.spring || 0.1, l = i.friction || 0.8, k = i.grivity || 0, i.wind || 0, j = i.minLength || 0, {
            items: [],
            timer: null,
            isPause: !1,
            addNode: function (f, d) {
                var m = {
                    node: f,
                    target: d,
                    vx: 0,
                    vy: 0
                };
                return this.items.push(m), this
            },
            play: function (d) {
                this.stop(), d = null == d ? 1000 / 24 : d;
                var c = this;
                this.timer = setInterval(function () {
                    c.nextFrame()
                }, d)
            },
            stop: function () {
                null != this.timer && window.clearInterval(this.timer)
            },
            nextFrame: function () {
                var I, H, G, F, E, q, p, f, d, c, b;
                for (I = 0; I < this.items.length; I++) {
                    H = this.items[I], G = H.node, F = H.target, E = H.vx, q = H.vy, p = F.x - G.x, f = F.y - G.y, d = Math.atan2(f, p), 0 != j ? (c = F.x - Math.cos(d) * j, b = F.y - Math.sin(d) * j, E += (c - G.x) * h, q += (b - G.y) * h) : (E += p * h, q += f * h), E *= l, q *= l, q += k, G.x += E, G.y += q, H.vx = E, H.vy = q
                }
            }
        }
    }

    function y(i, c) {
        function k() {
            return n = setInterval(function () {
                return x ? (m.stop(), void 0) : (i.rotate += l || 0.2, i.rotate > 2 * Math.PI && (i.rotate = 0), void 0)
            }, 100), m
        }

        function j() {
            return window.clearInterval(n), m.onStop && m.onStop(i), m
        }
        var n, m, l;
        return c.context, n = null, m = {}, l = c.v, m.run = k, m.stop = j, m.onStop = function (b) {
            return m.onStop = b, m
        }, m
    }

    function w(j, i) {
        function l() {
            return window.clearInterval(n), m.onStop && m.onStop(j), m
        }

        function k() {
            var b = i.dx || 0,
                a = i.dy || 2;
            return n = setInterval(function () {
                return x ? (m.stop(), void 0) : (a += o, j.y + j.height < p.stage.canvas.height ? j.setLocation(j.x + b, j.y + a) : (a = 0, l()), void 0)
            }, 20), m
        }
        var p = i.context,
            o = i.gravity || 0.1,
            n = null,
            m = {};
        return m.run = k, m.stop = l, m.onStop = function (b) {
            return m.onStop = b, m
        }, m
    }

    function v(a, q) {
        function n(G, F, E, j, i) {
            var b = new D.Node;
            return b.setImage(a.image), b.setSize(a.width, a.height), b.setLocation(G, F), b.showSelected = !1, b.dragable = !1, b.paint = function (c) {
                c.save(), c.arc(0, 0, E, j, i), c.clip(), c.beginPath(), null != this.image ? c.drawImage(this.image, -this.width / 2, -this.height / 2) : (c.fillStyle = "rgba(" + this.style.fillStyle + "," + this.alpha + ")", c.rect(-this.width / 2, -this.height / 2, this.width / 2, this.height / 2), c.fill()), c.closePath(), c.restore()
            }, b
        }

        function m(H, G) {
            var F = H,
                E = H + Math.PI,
                f = n(a.x, a.y, a.width, F, E),
                b = n(a.x - 2 + 4 * Math.random(), a.y, a.width, F + Math.PI, F);
            a.visible = !1, G.add(f), G.add(b), D.Animate.gravity(f, {
                context: G,
                dx: 0.3
            }).run().onStop(function () {
                G.remove(f), G.remove(b), o.stop()
            }), D.Animate.gravity(b, {
                context: G,
                dx: -0.2
            }).run()
        }

        function l() {
            return m(q.angle, p), o
        }

        function k() {
            return o.onStop && o.onStop(a), o
        }
        var o, p = q.context;
        return a.style, o = {}, o.onStop = function (b) {
            return o.onStop = b, o
        }, o.run = l, o.stop = k, o
    }

    function u(F, E) {
        function o(f) {
            f.visible = !0, f.rotate = Math.random();
            var c = p.stage.canvas.width / 2;
            f.x = c + Math.random() * (c - 100) - Math.random() * (c - 100), f.y = p.stage.canvas.height, f.vx = 5 * Math.random() - 5 * Math.random(), f.vy = -25
        }

        function i() {
            return o(F), n = setInterval(function () {
                return x ? (m.stop(), void 0) : (F.vy += q, F.x += F.vx, F.y += F.vy, (F.x < 0 || F.x > p.stage.canvas.width || F.y > p.stage.canvas.height) && (m.onStop && m.onStop(F), o(F)), void 0)
            }, 50), m
        }

        function d() {
            window.clearInterval(n)
        }
        var q = 0.8,
            p = E.context,
            n = null,
            m = {};
        return m.onStop = function (b) {
            return m.onStop = b, m
        }, m.run = i, m.stop = d, m
    }

    function t() {
        x = !0
    }

    function s() {
        x = !1
    }

    function r(M, L) {
        function f() {
            return i = setInterval(function () {
                if (x) {
                    return q.stop(), void 0
                }
                var b = K.y + I + Math.sin(F) * G;
                M.setLocation(M.x, b), F += E
            }, 100), q
        }

        function a() {
            window.clearInterval(i)
        }
        var I, H, G, F, E, q, i, K = L.p1,
            J = L.p2;
        return L.context, I = K.x + (J.x - K.x) / 2, H = K.y + (J.y - K.y) / 2, G = D.util.getDistance(K, J) / 2, F = Math.atan2(H, I), E = L.speed || 0.2, q = {}, i = null, q.run = f, q.stop = a, q
    }

    function g(i, d) {
        function l() {
            return m = setInterval(function () {
                var a, j, f, c;
                return x ? (n.stop(), void 0) : (a = p.x - i.x, j = p.y - i.y, f = a * o, c = j * o, i.x += f, i.y += c, 0.01 > f && 0.1 > c && k(), void 0)
            }, 100), n
        }

        function k() {
            window.clearInterval(m)
        }
        var o, n, m, p = d.position;
        return d.context, o = d.easing || 0.2, n = {}, m = null, n.onStop = function (b) {
            return n.onStop = b, n
        }, n.run = l, n.stop = k, n
    }

    function e(G, F) {
        function d() {
            return m = setInterval(function () {
                G.scaleX += q, G.scaleY += q, G.scaleX >= E && c()
            }, 100), n
        }

        function c() {
            n.onStop && n.onStop(G), G.scaleX = p, G.scaleY = o, window.clearInterval(m)
        }
        var E, q, p, o, n, m;
        return F.position, F.context, E = F.scale || 1, q = 0.06, p = G.scaleX, o = G.scaleY, n = {}, m = null, n.onStop = function (b) {
            return n.onStop = b, n
        }, n.run = d, n.stop = c, n
    }
    D.Animate = {}, D.Effect = {};
    var x = !1;
    D.Effect.spring = z, D.Effect.gravity = B, D.Animate.stepByStep = A, D.Animate.rotate = y, D.Animate.scale = e, D.Animate.move = g, D.Animate.cycle = r, D.Animate.repeatThrow = u, D.Animate.dividedTwoPiece = v, D.Animate.gravity = w, D.Animate.startAll = s, D.Animate.stopAll = t
}(JTopo),
function (g) {
    function j(t, s) {
        var q, p, o, n, m, l, k, r = [];
        return 0 == t.length ? r : (q = s.match(/^\s*(\w+)\s*$/), null != q ? (p = t.filter(function (b) {
            return b.elementType == q[1]
        }), null != p && p.length > 0 && (r = r.concat(p))) : (o = !1, q = s.match(/\s*(\w+)\s*\[\s*(\w+)\s*([>=<])\s*['"](\S+)['"]\s*\]\s*/), (null == q || q.length < 5) && (q = s.match(/\s*(\w+)\s*\[\s*(\w+)\s*([>=<])\s*(\d+(\.\d+)?)\s*\]\s*/), o = !0), null != q && q.length >= 5 && (n = q[1], m = q[2], l = q[3], k = q[4], p = t.filter(function (d) {
            if (d.elementType != n) {
                return !1
            }
            var c = d[m];
            return 1 == o && (c = parseInt(c)), "=" == l ? c == k : ">" == l ? c > k : "<" == l ? k > c : "<=" == l ? k >= c : ">=" == l ? c >= k : "!=" == l ? c != k : !1
        }), null != p && p.length > 0 && (r = r.concat(p)))), r)
    }

    function i(b) {
        var l, k, e;
        if (b.find = function (c) {
            return h.call(this, c)
        }, f.forEach(function (a) {
            b[a] = function (d) {
                for (var m = 0; m < this.length; m++) {
                    this[m][a](d)
                }
                return this
            }
        }), b.length > 0) {
            l = b[0];
            for (k in l) {
                e = l[k], "function" == typeof e && function (a) {
                    b[k] = function () {
                        var m, n = [];
                        for (m = 0; m < b.length; m++) {
                            n.push(a.apply(b[m], arguments))
                        }
                        return n
                    }
                }(e)
            }
        }
        return b.attr = function (n, m) {
            var q, p, o;
            if (null != n && null != m) {
                for (q = 0; q < this.length; q++) {
                    this[q][n] = m
                }
            } else {
                if (null != n && "string" == typeof n) {
                    for (p = [], q = 0; q < this.length; q++) {
                        p.push(this[q][n])
                    }
                    return p
                }
                if (null != n) {
                    for (q = 0; q < this.length; q++) {
                        for (o in n) {
                            this[q][o] = n[o]
                        }
                    }
                }
            }
            return this
        }, b
    }

    function h(a) {
        var c, k = [],
            d = [];
        return this instanceof g.Stage ? (k = this.childs, d = d.concat(k)) : this instanceof g.Scene ? k = [this] : d = this, k.forEach(function (b) {
            d = d.concat(b.childs)
        }), c = null, c = "function" == typeof a ? d.filter(a) : j(d, a), c = i(c)
    }
    CanvasRenderingContext2D.prototype.roundRect = function (l, k, o, n, m) {
        "undefined" == typeof m && (m = 5), this.beginPath(), this.moveTo(l + m, k), this.lineTo(l + o - m, k), this.quadraticCurveTo(l + o, k, l + o, k + m), this.lineTo(l + o, k + n - m), this.quadraticCurveTo(l + o, k + n, l + o - m, k + n), this.lineTo(l + m, k + n), this.quadraticCurveTo(l, k + n, l, k + n - m), this.lineTo(l, k + m), this.quadraticCurveTo(l, k, l + m, k), this.closePath()
    }, CanvasRenderingContext2D.prototype.dashedLineTo = function (x, w, v, u, t) {
        var s, r, q, p, o, n, m;
        for ("undefined" == typeof t && (t = 5), s = v - x, r = u - w, q = Math.floor(Math.sqrt(s * s + r * r)), p = 0 >= t ? q : q / t, o = r / q * t, n = s / q * t, this.beginPath(), m = 0; p > m; m++) {
            m % 2 ? this.lineTo(x + m * n, w + m * o) : this.moveTo(x + m * n, w + m * o)
        }
        this.stroke()
    }, g.Node.prototype.paint = function (b) {
        this.image ? null != this.image.alarm && null != this.alarm ? b.drawImage(this.image.alarm, -this.width / 2, -this.height / 2, this.width, this.height) : b.drawImage(this.image, -this.width / 2, -this.height / 2, this.width, this.height) : (b.beginPath(), b.fillStyle = "rgba(" + this.fillColor + "," + this.alpha + ")", b.rect(-this.width / 2, -this.height / 2, this.width, this.height), b.fill(), b.closePath()), this.paintText(b), null != this.alarm && this.paintAlarmText(b)
    }, g.Node.prototype.paintAlarmText = function (l) {
        var k, o, n, m;
        "" != this.alarm && (l.beginPath(), l.font = this.alarmFont || "10px 微软雅黑", k = l.measureText(this.alarm).width + 6, o = l.measureText("田").width + 6, n = this.width / 2 - k / 2, m = -this.height / 2 - o - 8, l.strokeStyle = "rgba(255,0,0, 0.5)", l.fillStyle = "rgba(255,0,0, 0.5)", l.lineCap = "round", l.lineWidth = 1, l.moveTo(n, m), l.lineTo(n + k, m), l.lineTo(n + k, m + o), l.lineTo(n + k / 2 + 6, m + o), l.lineTo(n + k / 2, m + o + 8), l.lineTo(n + k / 2 - 6, m + o), l.lineTo(n, m + o), l.lineTo(n, m), l.fill(), l.stroke(), l.closePath(), l.beginPath(), l.strokeStyle = "rgba(" + this.fontColor + ", " + this.alpha + ")", l.fillStyle = "rgba(" + this.fontColor + ", " + this.alpha + ")", l.fillText(this.alarm, n + 2, m + o - 4), l.closePath())
    };
    var f = "click,mousedown,mouseup,mouseover,mouseout,mousedrag,keydown,keyup".split(",");
    g.Stage.prototype.find = h, g.Scene.prototype.find = h
}(JTopo);