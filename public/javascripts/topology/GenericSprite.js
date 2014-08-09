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
        this.label = this.data[this.diagrammer.nodeLabelField];
        this.classname = 'GenericSprite';
        this.nodeDefaultWidth = config.nodeWidth||60;
        this.nodeDefaultHeight = config.nodeHeight||60;
        this.inLinks = [];
        this.outLinks = [];
        this.anchors = [];
        this.group = null;
        this.innerX = 0;
        this.innerY = 0;
        this.inAnchor = false;
        if(config.renderer){
            this.itemRenderer = config.renderer(this);
        }else{
            this.itemRenderer = this._defaultItemRenderer();
        }
        this.setWidth(this.itemRenderer.getWidth());
        this.setHeight(this.itemRenderer.getHeight());
        this._initListener();
        this.updateRenderer();
        this.expanded = false;
        if(config.nodes){
            this.expander = new Cloudwei.GenericGroup({id:config.id+'_expander',data:config.data},diagrammer);
            this.expander.hide();
            diagrammer.groupsLayer.add(this.expander);
            this.expander.nodes = config.nodes;
            this.expander.links = config.links;
            this.expander.initialized = false;
        }
        if(config.group){
        	var group1 = diagrammer.groupsLayer.get('#'+config.group)[0];
        	if(group1){
        		group1.addNode(this);
        	}
        }
    },

    _initExpander: function(){
        for(var i = 0; i < this.expander.nodes.length; i++){
            var childNodeCfg = this.expander.nodes[i];
            if(this.diagrammer.nodeRenderFunc) {
                childNodeCfg.renderer = this.diagrammer.nodeRenderFunc;
            }
            var childNode = new Cloudwei.GenericSprite(childNodeCfg,this.diagrammer);
            this.expander.addNode(childNode)
            childNode.hide();
            this.diagrammer.nodesLayer.add(childNode);
        }
        for(var i = 0; i < this.expander.links.length; i++){
            var linkConfig = this.expander.links[i];
            if(this.diagrammer.nodesLayer.get('#'+linkConfig.source)[0]
                && this.diagrammer.nodesLayer.get('#'+linkConfig.target)[0]){
                var link = new Cloudwei.GenericLink(linkConfig,this.diagrammer);
                link.hide();
                this.diagrammer.linksLayer.add(link);
            }
        }
        this.expander.initialized = true;
        delete this.expander.nodes;
        delete this.expander.links;
    },
    
    _initListener: function(){
    	this.on("dblclick",function(evt){
    		if(this.diagrammer.state == 'cursor' && this.expander){
                this.hideLinks(false);
                this.hide();
                if(!this.expander.initialized)
                    this._initExpander();
                for(var i = 0; i < this.expander.sprites.length; i++){
                    this.expander.sprites[i].show();
                    this.expander.sprites[i].showLinks();
                }
                this.expander.show();
                this.diagrammer.doLayout();
                this.expander.updateBounds();
                this.diagrammer.nodesLayer.draw();
                this.diagrammer.linksLayer.draw();
                this.diagrammer.groupsLayer.draw();
            }
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
        			newX = this.group.getAbsolutePosition().x;
        		if(pos.x + this.getWidth()*ratio > this.group.getAbsolutePosition().x + this.group.getWidth()*ratio)
        			newX = this.group.getAbsolutePosition().x+this.group.getWidth()*ratio-this.getWidth()*ratio;
        		if(pos.y < this.group.getAbsolutePosition().y)
        			newY = this.group.getAbsolutePosition().y;
        		if(pos.y + this.getHeight()*ratio > this.group.getAbsolutePosition().y + this.group.getHeight()*ratio)
        			newY = this.group.getAbsolutePosition().y+this.group.getHeight()*ratio-this.getHeight()*ratio;
        	}
        	return {
    			x:newX,
    			y:newY
    		};
        });
    },

    _defaultItemRenderer: function(){
        var text = new Kinetic.Text({
            text: this.label,
            width: this.nodeDefaultWidth,
            height: this.nodeDefaultHeight,
        	y: this.nodeDefaultHeight/2-5,
        	fill: 'white',
            align: "center"
        });

        var rect = new Kinetic.Rect({
            width: this.nodeDefaultWidth,
            height: this.nodeDefaultHeight,
            fill: '#00bfff',
            stroke: '#555',
            strokeWidth: 2,
            padding: 20
        });

        var defaultRenderer = new Kinetic.Group({
            id: this.id+"DefaultRenderer",
            width: this.nodeDefaultWidth,
            height: this.nodeDefaultHeight
        });
        defaultRenderer.add(rect);
        defaultRenderer.add(text);
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
    	for(var i=0;i<this.inLinks.length;i++){
    		this.inLinks[i].updateRenderer();
    	}
        for(var i=0;i<this.outLinks.length;i++){
    		this.outLinks[i].updateRenderer();
    	}
    	if(autoRefresh)
    		this.diagrammer.linksLayer.draw();
    },

    hideLinks: function(autoRefresh){
    	for(var i=0;i<this.inLinks.length;i++){
    		this.inLinks[i].hide();
    	}
        for(var i=0;i<this.outLinks.length;i++){
    		this.outLinks[i].hide();
    	}
    	if(autoRefresh)
    		this.diagrammer.linksLayer.draw();
    },

    showLinks: function(autoRefresh){
    	for(var i=0;i<this.inLinks.length;i++){
    		this.inLinks[i].show();
    	}
        for(var i=0;i<this.outLinks.length;i++){
    		this.outLinks[i].show();
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
    	var mouseX = evt.clientX;
    	var mouseY = evt.clientY;
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