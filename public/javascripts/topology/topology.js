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
    addNode: function(o){
        var node = new JTopo.Node(o.name);
        node.fontColor = 'black';
        node.showSelected = false;
        node.setImage(this.getIcon(o.type), true);
        this.scene.add(node);
        this.nodesMap[o.id] = node;
        return node;
    },
    addLink: function(o){
        var sourceNode = this.nodesMap[o.source];
        var targetNode = this.nodesMap[o.target];
        var link = new JTopo.Link(sourceNode, targetNode);
        link.strokeColor = '204,204,204';
        link.lineWidth = 3;
        this.scene.add(link);
        this.linksMap[o.id] = link;
        return link;
    },
    doLayout: function(){
        this.scene.doLayout(JTopo.layout.TreeLayout('down', 100, 150));
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
    }
}