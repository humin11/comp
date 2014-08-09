Cloudwei.GenericGroup = function(config,diagrammer){
    this._initialize(config,diagrammer);
};

Cloudwei.GenericGroup.prototype = {
	
	_initialize: function(config,diagrammer){
		config.draggable = true;
    	Kinetic.Group.call(this, config);
        this.diagrammer = diagrammer;
        this.data = config.data;
        this.classname = 'GenericGroup';
        this.label = this.data[this.diagrammer.nodeLabelField];
        this.sprites = [];
        this.groupDefaultWidth = config.groupWidth||400;
        this.groupDefaultHeight = config.groupHeight||300;
        if(config.renderer){
            this.itemRenderer = config.renderer;
        }else{
            this.itemRenderer = this._defaultItemRenderer();
        }
        this.group = null;
        this.innerX = 0;
        this.innerY = 0;
        this.setWidth(this.itemRenderer.getWidth());
        this.setHeight(this.itemRenderer.getHeight())
        this._initListener();
        this.updateRenderer();
	},
	
	_initListener: function(){
		this.on("dragstart",function(){
			this.setOpacity(0.5);
		});
		this.on("dragend",function(){
			this.setOpacity(1);
			this.diagrammer.groupsLayer.draw();
            this.diagrammer.nodesLayer.draw();
		});
		this.on("dragmove",function(evt){
			if(this.group){
        		this.innerX = this.getX()-this.group.getX();
        		this.innerY = this.getY()-this.group.getY();
        	}
			this._handleDragging();
        });
        this.on("mousedown",function(evt){
        	this.moveToTop();
        	for(var i=0;i<this.sprites.length;i++){
        		if(this.sprites[i].classname == 'GenericGroup' && 
        			(this.sprites[i].getWidth() < this.getWidth() || 
        				this.sprites[i].getHeight() < this.getHeight()) && 
        				this.sprites[i].getZIndex()<this.getZIndex()){
        			this.moveToBottom();
        		}
        	}
        });
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
	
	_handleDragging: function(){
		for(var i=0;i<this.sprites.length;i++){
			var node = this.sprites[i];
			node.moveToTop();
			node.displace(this.getX() + node.innerX,this.getY() + node.innerY,true);
		}
	},
    
	_defaultItemRenderer: function(){
        var text = new Kinetic.Text({
            text: this.label,
            width: this.groupDefaultWidth,
            y:20,
        	fill: 'green',
            align: "center"
        });
        var rect = new Kinetic.Rect({
            width: this.groupDefaultWidth,
            height: this.groupDefaultHeight,
            fill: '#ddd',
            stroke: '#555',
            strokeWidth: 2,
            padding: 20,
            cornerRadius: 10
        });   
        var defaultRenderer = new Kinetic.Group({
            id: this.id+"DefaultGroupRenderer",
            width: this.groupDefaultWidth,
            height: this.groupDefaultHeight
        });
        defaultRenderer.add(rect);
        defaultRenderer.add(text);
        defaultRenderer.updateSize = function(w,h){
            this.setWidth(w);
            this.setHeight(h);
            this.getChildren().each(function(child){
                child.setWidth(w);
                child.setHeight(h);
            });
        }
        return defaultRenderer;
    },
    
    displace: function(x,y,autoRefresh){
    	this.setX(x);
		this.setY(y);
		for(var i=0;i<this.sprites.length;i++){
			var node = this.sprites[i];
			node.displace(x+node.innerX,y+node.innerY,true);
		}
    },
    
    addNode: function(node){
    	node.group = this;
    	this.sprites.push(node);
    },

    updateBounds: function(){
        var gX, gY, gW, gH;
        for(var i=0;i<this.sprites.length;i++){
			var node = this.sprites[i];
            var nodeX = node.getX();
            var nodeY = node.getY();
            var nodeWidth = node.getWidth();
            var nodeHeight = node.getHeight();
            if(i == 0){
                gX = nodeX;
                gY = nodeY;
                gW = nodeX + nodeWidth;
                gH = nodeY + nodeHeight;
            }
            if(gX > nodeX)
                gX = nodeX;
            if(gY > nodeY)
                gY = nodeY;
            if(gW < nodeX + nodeWidth)
                gW = nodeX + nodeWidth;
            if(gH < nodeY + nodeHeight)
                gH = nodeY + nodeHeight;
		}
        gW = gW - gX;
        gH = gH - gY;
        this.setX(gX);
        this.setY(gY);
        this.updateSize(gW, gH);
        for(var i=0;i<this.sprites.length;i++){
			var node = this.sprites[i];
            node.innerX = node.getX() - gX;
            node.innerY = node.getY() - gY;
        }
    },

    updateSize: function(w,h){
        this.setWidth(w);
        this.setHeight(h);
        this.itemRenderer.updateSize(w,h);
    },
    
    updateRenderer: function(){
        this.removeChildren();
        this.add(this.itemRenderer);
    }
	
};
Kinetic.Util.extend(Cloudwei.GenericGroup, Kinetic.Group);