Cloudwei.NodeRenderer = function(node){
	this._initialize(node);
};

Cloudwei.NodeRenderer.prototype = {

	_initialize: function(node){
		Kinetic.Group.call(this, {});
        this.diagrammer = node.diagrammer;
        this.iconlib = node.diagrammer.iconlib;
		var imageObj = this.getIcon(node.data.type);
        var imgWidth = imageObj.width;
        var imgHeight = imageObj.height;
        var networkItem = new Kinetic.Image({
            image: imageObj,
            x: 0,
            y: 0
        });
        var text = new Kinetic.Text({
            text: node.label,
            y:imgHeight,
            fill: 'black',
            align: "center"
        });
        if(text.getWidth()<imgWidth)
            text.setWidth(imgWidth);
        this.setWidth(imgWidth);
        this.setHeight(imgHeight+text.getHeight());
        this.add(networkItem);
        this.add(text);
	},

    getIcon: function(type){
        if(type == 'server')
            return this.iconlib.getResult(type);
        else if(type == 'hypervisor')
            return this.iconlib.getResult(type);
        else if(type == 'vm')
            return this.iconlib.getResult(type);
        else if(type == 'hba')
            return this.iconlib.getResult(type);
        else if(type == 'fabric')
            return this.iconlib.getResult(type);
        else if(type == 'switch')
            return this.iconlib.getResult(type);
        else if(type == 'zone')
            return this.iconlib.getResult(type);
        else if(type == 'port_up'||type == 'port_down')
            return this.iconlib.getResult(type);
        else if(type == 'storage')
            return this.iconlib.getResult(type);
        else if(type == 'controller')
            return this.iconlib.getResult(type);
        else if(type == 'volume')
            return this.iconlib.getResult(type);
        else if(type == 'pool')
            return this.iconlib.getResult(type);
        else if(type == 'raidgroup')
            return this.iconlib.getResult(type);
        else if(type == 'disk')
            return this.iconlib.getResult(type);
        else if(type == 'agent')
            return this.iconlib.getResult(type);
        else if(type == 'tape')
            return this.iconlib.getResult(type);
        else if(type == 'ds6000')
            return this.iconlib.getResult(type);
        else if(type == 'ds8000')
            return this.iconlib.getResult(type);
        else if(type == 'ess800')
            return this.iconlib.getResult(type);
        else if(type == 'xiv')
            return this.iconlib.getResult(type);
        else if(type == 'svc')
            return this.iconlib.getResult(type);
        else if(type == 'replication')
            return this.iconlib.getResult(type);
        else
            return this.iconlib.getResult('unknown');
    }

};
Kinetic.Util.extend(Cloudwei.NodeRenderer, Kinetic.Group);