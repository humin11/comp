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
        var node = this.createNode(o.name, o.type);
        var group = this.createGroup(o.name, o.nodes, o.links);
        var inlinks,outlinks;
        group.dbclick(function(event){
            for(var i=0;i<group.childs.length;i++){
                scene.remove(group.childs[i]);
            }
            scene.remove(this);
            scene.add(node);
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
        });

        node.dbclick(function(event) {
            inlinks = this.inLinks;
            outlinks = this.outLinks;
            group.x = this.x;
            group.y = this.y;
            scene.remove(this);
            scene.add(group);
            for(var i=0;i<group.expandNodes.length;i++){
                var node = diagrammer.createNode(group.expandNodes[i].name,group.expandNodes[i].type);
                node.setLocation(group.x,group.y);
                diagrammer.nodesMap[group.expandNodes[i].id] = node;
                group.add(node);
                scene.add(node);
            }
            for(var i=0;i<group.expandedLinks.length;i++){
                var link = group.expandedLinks[i];
                diagrammer.addLink(link);
            }
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
        var node = this.createNode(o.name, o.type);
        this.scene.add(node);
        this.nodesMap[o.id] = node;
        return node;
    },
    createNode: function(name,type){
        var node = new JTopo.Node(name);
        node.fontColor = 'black';
        node.showSelected = false;
        node.setImage(this.getIcon(type), true);
        node.type = type;
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
    getRank: function(type){
        if(type == 'server')
            return 1;
        else if(type == 'switch')
            return 2;
        else if(type == 'fabric')
            return 2;
        else if(type == 'storage')
            return 3;
        else
            return 1;
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
    doLayout: function(){
        var rankArr = [];
        var canvasWidth = this.scene.getBound().width;
        var vGap = 100;
        var hGap = 100;
        for(var i in this.nodesMap){
            var node = this.nodesMap[i];
            if(node.inLinks.length == 0)
                node.rank = 1;
            else if(node.inLinks.length > 0 && node.outLinks.length > 0)
                node.rank = 2;
            else if(node.inLinks.length > 0 && node.outLinks.length == 0)
                node.rank = 3;
        }
        for(var i in this.nodesMap){
            var node = this.nodesMap[i];
            var nodeRank = node.rank+this.getRank(node.type);
            var len = rankArr.length;
            if(rankArr.length<nodeRank)
                for(var j = 0;j<nodeRank-len;j++)
                    rankArr.push([]);
            var arr = rankArr[nodeRank-1];
            arr.push(node);
            rankArr.splice(-1,1,arr);
        }
        for(var i=0;i<rankArr.length;i++){
            var rankNodes = rankArr[i];
            var currRank = 0;
            if(rankNodes.length > 0){
                var startW = (canvasWidth-rankNodes.length*vGap)/(rankNodes.length+1);
                var startY = (i+currRank) * hGap;
                for(var j=0;j<rankNodes.length;j++){
                    var startX = (j+1)*startW;
                    var node = rankNodes[j];
                    node.x = startX;
                    node.y = startY;
                }
            }
        }
    }
}
