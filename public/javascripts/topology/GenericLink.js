Cloudwei.GenericLink = function(config,diagrammer){
    this._initialize(config,diagrammer);
};

Cloudwei.GenericLink.prototype = {

    _initialize: function(config,diagrammer){
        config.strokeWidth = 1.5;
        config.stroke = "#638cc3";
        config.lineCap = "round";
        config.opacity = 0.3;
        if(config.dashline)
        	config.dashArray = [5,5];
        config.shadow = {
	        color: 'black',
	        blur: 5,
	        offset: [2, 2],
	        opacity: 0.5
	    };
        this.source = diagrammer.nodesLayer.get('#'+config.source)[0];
        this.target = diagrammer.nodesLayer.get('#'+config.target)[0];
        this.diagrammer = diagrammer;
        if(config.sourceAnchor)
        	this.sourceAnchor = this.source.get('#'+config.sourceAnchor)[0];
        if(config.targetAnchor)
        	this.targetAnchor = this.target.get('#'+config.targetAnchor)[0];
        this.source.links.push(this);
        this.target.links.push(this);
        this.highlight = false;
        this.classname = 'GenericLink';
        Kinetic.Line.call(this,config);
        this._initListener();
        this.updateRenderer();
    },
    
    _initListener: function(){
    	this.on("click",function(evt){
    	
        });
        this.on("dblclick",function(evt){
        	if(this.diagrammer.state == 'cursor')
    			alert(this.getId());
        });
    },

    updateRenderer: function(){
    	var sourceCenterX = this.source.getX()+this.source.getWidth()/2;
    	var sourceCenterY = this.source.getY()+this.source.getHeight()/2;
    	var targetCenterX = this.target.getX()+this.target.getWidth()/2;
    	var targetCenterY = this.target.getY()+this.target.getHeight()/2;
    	if(this.sourceAnchor){
    		sourceCenterX = this.source.getX()+this.sourceAnchor.getX()+this.sourceAnchor.getWidth()/2;
    		sourceCenterY = this.source.getY()+this.sourceAnchor.getY()+this.sourceAnchor.getHeight()/2;
    	}
    	if(this.targetAnchor){
    		targetCenterX = this.target.getX()+this.targetAnchor.getX()+this.targetAnchor.getWidth()/2;
    		targetCenterY = this.target.getY()+this.targetAnchor.getY()+this.targetAnchor.getHeight()/2;
    	}
        this.setPoints([sourceCenterX,sourceCenterY,targetCenterX,targetCenterY]);
    }
 
};
Kinetic.Util.extend(Cloudwei.GenericLink, Kinetic.Line);