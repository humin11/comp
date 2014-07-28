Diagrammer = function(config){
    this._initialize(config);
};

Diagrammer.prototype = {
    ICONS_PATH: '/assets/images/devices/',
    nodesMap: {},
    linksMap: {},
    _initialize: function(config){
        this.canvas = document.getElementById(config.id);
        this.stage = new JTopo.Stage(this.canvas);
        this.scene = new JTopo.Scene(this.stage);
    },
    addGroup: function(o){
        var scene = this.scene;
        var diagrammer = this;
        var node = this.createNode(o.id,o.name, o.type);
        var group = this.createGroup(o.name, o.nodes, o.links);
        var inlinks,outlinks;
        group.dbclick(function(event){
            for(var i=0;i<group.childs.length;i++){
                delete diagrammer.nodesMap[group.childs[i].id];
                scene.remove(group.childs[i]);
            }
            scene.remove(this);
            scene.add(node);
            diagrammer.nodesMap[o.id] = node;
            if(inlinks){
                for(var i=0;i<inlinks.length;i++){
                    var link = diagrammer.createLink(inlinks[i].nodeA,node);
                    scene.add(link);
                }
            }
            if(outlinks){
                for(var i=0;i<outlinks.length;i++){
                    var link = diagrammer.createLink(node,outlinks[i].nodeZ);
                    scene.add(link);
                }
            }
            diagrammer.doLayout();
        });

        node.dbclick(function(event) {
            inlinks = this.inLinks;
            outlinks = this.outLinks;
            group.x = this.x;
            group.y = this.y;
            scene.remove(this);
            delete diagrammer.nodesMap[o.id];
            scene.add(group);
            for(var i = 0; i < group.expandNodes.length; i++){
                var node = diagrammer.createNode(group.expandNodes[i].id,group.expandNodes[i].name,group.expandNodes[i].type);
                node.setLocation(group.x,group.y);
                diagrammer.nodesMap[group.expandNodes[i].id] = node;
                group.add(node);
                scene.add(node);
            }
            for(var i = 0; i < group.expandedLinks.length; i++){
                var link = group.expandedLinks[i];
                diagrammer.addLink(link);
            }
            diagrammer.doLayout();
        });
        this.scene.add(node);
        this.nodesMap[o.id] = node;
    },
    createGroup: function(name,nodes,links){
        var group = new JTopo.Container(name);
        group.textPosition = 'Middle_Center';
        group.fontColor = '100,255,0';
        group.font = '30pt';
        group.expandNodes = nodes;
        group.expandedLinks = links;
        return group;
    },
    addNode: function(o){
        var node = this.createNode(o.id,o.name, o.type);
        this.scene.add(node);
        this.nodesMap[o.id] = node;
        return node;
    },
    createNode: function(id,name,type){
        var node = new JTopo.Node(name);
        node.fontColor = 'black';
        node.showSelected = false;
        node.setImage(this.getIcon(type), true);
        node.type = type;
        node.id = id;
        return node;
    },
    addLink: function(o){
        var sourceNode = this.nodesMap[o.source];
        var targetNode = this.nodesMap[o.target];
        var link = this.createLink(sourceNode,targetNode);
        link.source = o.source;
        link.target = o.target;
        this.scene.add(link);
        return link;
    },
    createLink: function(sourceNode,targetNode){
        var link = new JTopo.Link(sourceNode, targetNode);
        link.strokeColor = '204,204,204';
        link.lineWidth = 3;
        return link;
    },
    getIcon: function(type){
        if(type == 'server')
            return this.ICONS_PATH+'server_128.png';
        else if(type == 'switch')
            return this.ICONS_PATH+'switch_128.png';
        else if(type == 'fabric')
            return this.ICONS_PATH+'fabric_128.png';
        else if(type == 'storage')
            return this.ICONS_PATH+'storage_128.png';
        else
            return this.ICONS_PATH+'unknown_128.png';
    },
    getOrder: function(){
        return 'server,fabric,switch,storage'.split(',');
    },
    rootRank:function(nodeArr){
        for(var i = 0; i < nodeArr.length; i++){
            var isRoot = true;
            for(var j = 0; j < nodeArr[i].inLinks.length; j++){
                for(var k = 0; k < nodeArr.length; k++){
                    if(nodeArr[i].inLinks[j].nodeA.id == nodeArr[k].id){
                        isRoot = false;
                    }
                }
            }
            if(isRoot){
                this.childRank(nodeArr[i],nodeArr);
            }
        }
    },
    childRank:function(sourceNode,nodeArr){
        for(var i = 0; i < sourceNode.outLinks.length; i++){
            var targetNode = sourceNode.outLinks[i].nodeZ;
            targetNode.rank = sourceNode.rank+1;
            for(var j = 0; j < nodeArr.length; j++){
                if(targetNode.id == nodeArr[j].id){
                    this.childRank(targetNode,nodeArr);
                    break;
                }
            }
        }
    },
    doLayout: function(){
        var canvasWidth = this.scene.getBound().width;
        var vGap = 100;
        var hGap = 100;
        var nodeWidth = 32;
        var typeMap = {};
        var rankRow = 0;
        for(var i in this.nodesMap){
            var node = this.nodesMap[i];
            node.rank = 0;
            if(typeMap[node.type]){
                typeMap[node.type].push(node);
            }else{
                typeMap[node.type] = [node];
            }
        }
        var types = this.getOrder();
        for(var i = 0; i < types.length; i++) {
            var rankNodes = typeMap[types[i]];
            if (rankNodes) {
                this.rootRank(rankNodes);
            }
        }
        for(var i = 0; i < types.length; i++){
            var rankNodes = typeMap[types[i]];
            if(rankNodes){
                rankNodes.sort(function(a,b){
                    if(a.rank > b.rank)
                        return 1;
                    else if(a.rank < b.rank)
                        return -1;
                    else
                        return 0;
                });
                var rankMap = {};
                for(var j = 0; j < rankNodes.length; j++){
                    if(rankMap["rank"+rankNodes[j].rank])
                        rankMap["rank"+rankNodes[j].rank].push(rankNodes[j]);
                    else
                        rankMap["rank"+rankNodes[j].rank] = [rankNodes[j]];
                }
                for(var j in rankMap){
                    var nodes = rankMap[j];
                    var startX = (canvasWidth - (nodeWidth + vGap) * nodes.length) / 2 + vGap;
                    var startY = rankRow * hGap;
                    for(var k = 0; k < nodes.length; k++){
                        var node = nodes[k];
                        node.x = startX;
                        node.y = startY;
                        startX += (nodeWidth+vGap);
                    }
                    rankRow++;
                }

            }
        }
    }
}
