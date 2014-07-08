Cloudwei.GenericSprite = function(config,diagrammer){
    this._initialize(config,diagrammer);
};

Cloudwei.GenericSprite.prototype = {

    _initialize: function(config,diagrammer){
    	config.draggable = true;
    	Kinetic.Group.call(this, config);
        this.enableTooltip = true;
        this.diagrammer = diagrammer;
        this.data = config.data;
        this.classname = 'GenericSprite';
        if(config.renderer){
            this.itemRenderer = config.renderer;
        }else{
            this.itemRenderer = this._defaultItemRenderer();
        }
        this.setWidth(this.itemRenderer.getWidth());
        this.setHeight(this.itemRenderer.getHeight());
        this.links = [];
        this.anchors = [];
        this.group = null;
        this.innerX = 0;
        this.innerY = 0;
        this.inAnchor = false;
        if(config.anchors && config.anchors.constructor == window.Array){
        	for(var i=0;i<config.anchors.length;i++){
        		this.addAnchor(config.anchors[i]);
        	}
        }
        this._initListener();
        this.updateRenderer();
        if(config.group){
        	var group1 = diagrammer.groupsLayer.get('#'+config.group)[0];
        	if(group1){
        		group1.addNode(this);
        	}
        }
    },
    
    _initListener: function(){
    	this.on("dblclick",function(evt){
    		if(this.diagrammer.state == 'cursor')
    			alert(this.getId());
    	});
    	this.on("mousedown",function(evt){
        	this.diagrammer.destroyTooltip();
        	this.moveToTop();
        	if(this.group){
        		this.group.moveToTop();
        		this.group.getParent().draw();
        	}
        });
        this.on("dragmove",function(evt){
        	if(this.group){
        		this.innerX = this.getX()-this.group.getX();
        		this.innerY = this.getY()-this.group.getY();
        	}
        	this.updateLinks(true);
        });
        if(this.enableTooltip){
        	this.on("mouseover",function(evt){
        		if(!this.inAnchor)
        			this.createTooltip(evt.evt);
        	});
        	this.on("mouseout",function(evt){
        		if(!this.inAnchor)
        			this.diagrammer.destroyTooltip();
        	});
        }
        this.setDragBoundFunc(function(pos){
        	var ratio = this.diagrammer.ratio;
        	var newX = pos.x;
        	var newY = pos.y;
        	if(this.group){
        		if(pos.x < this.group.getAbsolutePosition().x)
        			newX = this.group.getAbsolutePosition().x+5;
        		if(pos.x + this.getWidth()*ratio > this.group.getAbsolutePosition().x + this.group.getWidth()*ratio)
        			newX = this.group.getAbsolutePosition().x+this.group.getWidth()*ratio-this.getWidth()*ratio-5;
        		if(pos.y < this.group.getAbsolutePosition().y)
        			newY = this.group.getAbsolutePosition().y+5;
        		if(pos.y + this.getHeight()*ratio > this.group.getAbsolutePosition().y + this.group.getHeight()*ratio)
        			newY = this.group.getAbsolutePosition().y+this.group.getHeight()*ratio-this.getHeight()*ratio-5;
        	}
        	return {
    			x:newX,
    			y:newY
    		};
        });
    },

    _defaultItemRenderer: function(){
        var defaultRenderer = new Kinetic.Text({
            id: this.id+"DefaultRenderer",
            text: this.data[this.diagrammer.nodeLabelField],
            stroke: '#555',
        	strokeWidth: 5,
        	padding: 20,
        	fill: '#ddd',
            align: "center",
        	textFill: 'green',
	        cornerRadius: 10
        });
        return defaultRenderer;
    },
    
    _defaultTooltip: function(){
    	var defaultTooltip = new Cloudwei.NodeTooltip();
		return defaultTooltip;
    },
    
    _defaultAnchorRenderer: function(){
    	var knob = new Kinetic.Circle({
    		radius: 2,
	        fill: 'black',
	        stroke: '638cc3',
	        strokeWidth: 1
    	});
    	return knob;
    },
    
    addAnchor: function(config){
    	var anchor = this._defaultAnchorRenderer();
    	anchor.setId(config.id);
    	anchor.data = config.data;
    	anchor.setX(config.x);
    	anchor.setY(config.y);
    	var node = this;
    	anchor.on("mouseover",function(evt){
    		node.inAnchor = true;
    		var tooltip = node._defaultTooltip();
            var t = "Port : "+this.getId();
    		tooltip.setText(t);
            tooltip.setWidth(t.length*10); 
            tooltip.setHeight(20);
    		node.updateTooltipPosition(tooltip,evt.evt);
    		node.diagrammer.showTooltip(tooltip);
    	});
    	anchor.on("mouseout",function(evt){
    		node.inAnchor = false;
    		node.diagrammer.destroyTooltip();
    	});
    	this.anchors.push(anchor);
    },

    displace: function(x,y,autoRefresh){
		this.setX(x);
		this.setY(y);
		if(autoRefresh)
			this.getParent().draw();
		this.updateLinks(autoRefresh);
    },
    
    updateLinks: function(autoRefresh){
    	for(var i=0;i<this.links.length;i++){
    		this.links[i].updateRenderer();
    	}
    	if(autoRefresh)
    		this.diagrammer.linksLayer.draw();
    },

    updateRenderer: function(){
        this.removeChildren();
        this.add(this.itemRenderer);
        for(var i=0;i<this.anchors.length;i++){
        	this.add(this.anchors[i]);
        }
    },
    
    createTooltip: function(evt){
    	var tooltip = this._defaultTooltip();
    	var tooltipFields = this.diagrammer.nodeTooltipFields;
    	var fieldsArray = tooltipFields.split('|');
    	var text = '';
        var maxW = 0;
        var maxH = fieldsArray.length * 20;
    	for(var i=0;i<fieldsArray.length;i++){
            var t = (fieldsArray[i] + ' : ' + this.data[fieldsArray[i]]);
            if(t.length*10 > maxW)
                maxW = t.length*10;
    		text += t;
    		text += '\n';
    	}
    	tooltip.setText(text);
        tooltip.setWidth(maxW); 
        tooltip.setHeight(maxH);
    	this.updateTooltipPosition(tooltip,evt);
    	this.diagrammer.showTooltip(tooltip);
    },
    
    updateTooltipPosition: function(tooltip,evt){
    	var mouseX = evt.offsetX;
    	var mouseY = evt.offsetY;
    	var canvasWidth = this.diagrammer.getWidth();
    	var canvasHeight = this.diagrammer.getHeight();
    	var tooltipWidth = tooltip.getWidth();
    	var tooltipHeight = tooltip.getHeight();
    	var pX = mouseX-this.diagrammer.getX();
    	var pY = mouseY-this.diagrammer.getY();
    	if(mouseX+tooltipWidth > canvasWidth)
    		pX = mouseX-tooltipWidth-this.diagrammer.getX();
    	if(mouseY+tooltipHeight > canvasHeight)
    		pY = mouseY-tooltipHeight-this.diagrammer.getY();
    	tooltip.setX(pX);
    	tooltip.setY(pY);
    }
    

};
Kinetic.Util.extend(Cloudwei.GenericSprite, Kinetic.Group);