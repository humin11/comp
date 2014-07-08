var Cloudwei = {};
Cloudwei.Diagrammer = function(config){
    this._initialize(config);
};

Cloudwei.Diagrammer.prototype = {

    _initialize: function(config){
    	Kinetic.Stage.call(this, config);
    	this.nodeRenderFunc = config.nodeRenderFunc;
    	this.groupRenderFunc = config.groupRenderFunc;
        this.nodeLabelField = config.nodeLabelField||"name";
        this.nodeTooltipFields = config.nodeTooltipFields||"name|type|description";
        this.nodeDoubleClickFunc = config.nodeDoubleClickFunc;
        this.tooltip = null;
        this.ratio = 1;
        this.state = 'cursor';
        this.beyond = false;
        this.nodesLayer = new Kinetic.Layer();
        this.linksLayer = new Kinetic.Layer();
        this.groupsLayer = new Kinetic.Layer();
        this.tooltipLayer = new Kinetic.Layer();
        this.add(this.tooltipLayer);
        this.add(this.nodesLayer);
        this.add(this.groupsLayer);
        this.add(this.linksLayer);
        this.nodesLayer.setZIndex(2);
        this.linksLayer.setZIndex(1);
        this.tooltipLayer.setZIndex(3);
        this.groupsLayer.setZIndex(0);
        this._initListener();
    },
    
    _initListener: function(){
    	this.on("dragmove",function(evt){

    	});
    },

    addNode: function(nodeConfig){
    	if(this.nodeRenderFunc){
    		nodeConfig.renderer = this.nodeRenderFunc(nodeConfig);
    	}
        var node = new Cloudwei.GenericSprite(nodeConfig,this);
        this.nodesLayer.add(node);
        this.nodesLayer.draw();
        return node;
    },

    addLink: function(linkConfig){
    	var link = new Cloudwei.GenericLink(linkConfig,this);
        this.linksLayer.add(link);
        this.linksLayer.draw();
        return link;
    },
    
    addGroup: function(groupConfig){
    	if(this.groupRenderFunc){
    		groupConfig.renderer = this.groupRenderFunc(groupConfig);
    	}
    	var group = new Cloudwei.GenericGroup(groupConfig,this);
        this.groupsLayer.add(group);
        this.groupsLayer.draw();
        return group;
    },
    
    groupElements: function(groupConfig,nodesArray){
    	var group = this.groupsLayer.get('#'+groupConfig.id)[0];
    	if(!group){
    		if(this.groupRenderFunc){
	    		groupConfig.renderer = this.groupRenderFunc(groupConfig);
	    	}
    		group = new Cloudwei.GenericGroup(groupConfig,this);
    		this.groupsLayer.add(group);
    	}
    	for(var i=0;i<nodesArray.length;i++){
    		group.addNode(nodesArray[i]);
    	}
    	this.refresh();
    	return group;
    },
    
    showTooltip: function(customTooltip){
    	if(this.state == 'pan')
    		return;
    	if(this.tooltip){
    		this.destroyTooltip();
    	}
    	this.tooltipLayer.add(customTooltip);
    	this.tooltipLayer.draw();
    	this.tooltip = customTooltip;
    },
    
    destroyTooltip: function(){
    	this.tooltipLayer.removeChildren();
    	this.tooltipLayer.draw();
    	this.tooltip = null;
    },
    
    zoom: function(zoomRatio,reset){
    	if(!reset)
    		this.ratio = this.ratio*zoomRatio;
    	else
    		this.ratio = zoomRatio;
    	this.nodesLayer.setScale(this.ratio);
    	this.linksLayer.setScale(this.ratio);
    	this.groupsLayer.setScale(this.ratio);
    	this.nodesLayer.draw();
    	this.linksLayer.draw();
    	this.groupsLayer.draw();
    },
    
    setCursor: function(){
    	this.setDraggable(false);
    	this.content.style.cursor = 'default';
    	this.state = 'cursor';
    },
    
    setPan: function(){
    	this.setDraggable(true);
    	this.content.style.cursor = 'move';
    	this.state = 'pan';
    },
    
    autoFit: function(){
    	this.move(-(this.getX()),-(this.getY()));
    	var diagrammerBound = this.getDiagrammerBound();
    	var startPoint = {
    		x: (diagrammerBound.width/2+diagrammerBound.x), 
    		y: (diagrammerBound.height/2+diagrammerBound.y)
    	};
    	var endPoint = {
    		x:(this.getWidth()/2),
    		y:(this.getHeight()/2)
    	};
    	var deltaX = endPoint.x - startPoint.x;
    	var deltaY = endPoint.y - startPoint.y;
	    this.move(deltaX,deltaY);
    	var newRatio = Math.min(this.getWidth()/diagrammerBound.width,this.getHeight()/diagrammerBound.height);
    	this.zoom(newRatio,true);
    },
    
    getDiagrammerBound: function(){
    	var diagrammerBound = {
    		x:this.getX()+this.getWidth(),
    		y:this.getY()+this.getHeight(),
    		width:0,
    		height:0
    	};
    	var groups = this.groupsLayer.getChildren();
    	for(var i=0;i<groups.length;i++){
    		var group = groups[i];
    		if(group.getX() < diagrammerBound.x)
    			diagrammerBound.x = group.getX();
    		if(group.getY() < diagrammerBound.y)
    			diagrammerBound.y = group.getY();
    		if(group.getX()+group.getWidth() > diagrammerBound.width)
    			diagrammerBound.width = group.getX()+group.getWidth();
    		if(group.getY()+group.getHeight() > diagrammerBound.height)
    			diagrammerBound.height = group.getY()+group.getHeight();
    	}
    	var nodes = this.nodesLayer.getChildren();
    	for(var i=0;i<nodes.length;i++){
    		var node = nodes[i];
    		if(node.group)
    			continue;
    		if(node.getX() < diagrammerBound.x) 
    			diagrammerBound.x = node.getX();
    		if(node.getY() < diagrammerBound.y)
    			diagrammerBound.y = node.getY();
    		if(node.getX()+node.getWidth() > diagrammerBound.width)
    			diagrammerBound.width = node.getX()+node.getWidth();
    		if(node.getY()+node.getHeight() > diagrammerBound.height)
    			diagrammerBound.height = node.getY()+node.getHeight();
    	}
    	diagrammerBound.width = diagrammerBound.width-diagrammerBound.x;
    	diagrammerBound.height = diagrammerBound.height-diagrammerBound.y;
    	return diagrammerBound;
    },
    
    refresh: function(){
    	this.nodesLayer.draw();
    	this.linksLayer.draw();
    	this.groupsLayer.draw();
    },

    removeAll: function(){
        this.nodesLayer.removeChildren();
        this.nodesLayer.draw();
        this.linksLayer.removeChildren();
    	this.linksLayer.draw();
        this.groupsLayer.removeChildren();
    	this.groupsLayer.draw();
    }
    

};
Kinetic.Util.extend(Cloudwei.Diagrammer, Kinetic.Stage);