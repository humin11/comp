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
        this.widthRatio = this.groupDefaultWidth/this.diagrammer.getWidth(); 
        this.heightRatio = this.groupDefaultHeight/this.diagrammer.getHeight();
        this.setWidth(this.itemRenderer.getWidth());
        this.setHeight(this.itemRenderer.getHeight());
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
	
	_handleDragging: function(){
		for(var i=0;i<this.sprites.length;i++){
			var node = this.sprites[i];
			node.moveToTop();
			node.displace(this.getX() + node.innerX,this.getY() + node.innerY,true);
		}
		this.diagrammer.refresh();
	},
    
	_defaultItemRenderer: function(){
        var text = new Kinetic.Text({
            text: this.data[this.diagrammer.nodeLabelField],
            width: this.groupDefaultWidth,
            height: this.groupDefaultHeight,
        	padding: 20,
        	fill: 'green',
            align: "center"
        });

        var rect = new Kinetic.Rect({
            width: this.groupDefaultWidth,
            height: this.groupDefaultHeight,
            fill: '#edf0f1',
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
        return defaultRenderer;
    },
    
    displace: function(x,y,autoRefresh){
    	this.setX(x);
		this.setY(y);
		for(var i=0;i<this.sprites.length;i++){
			var node = this.sprites[i];
			node.displace(x+node.innerX,y+node.innerY,false);
		}
    },
    
    addNode: function(node){
    	var x = this.getX();
    	var y = this.getY();
    	node.innerX = parseInt(node.getX()*this.widthRatio);
    	node.innerY = parseInt(node.getY()*this.heightRatio);
    	node.displace(x+node.innerX,y+node.innerY,false);
    	node.group = this;
    	this.sprites.push(node);
    	if(node.classname == 'GenericGroup'){
    		if((node.getWidth() < this.getWidth() || 
					node.getHeight() < this.getHeight()) && 
						node.getZIndex()<this.getZIndex()){
				this.setZIndex(node.getZIndex()-1);
			}
		}
    },
    
    updateRenderer: function(){
        this.removeChildren();
        this.add(this.itemRenderer);
    }
	
};
Kinetic.Util.extend(Cloudwei.GenericGroup, Kinetic.Group);