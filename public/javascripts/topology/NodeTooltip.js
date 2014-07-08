Cloudwei.NodeTooltip = function(config){
	this._initialize(config);
};

Cloudwei.NodeTooltip.prototype = {

	_initialize: function(config){
		Kinetic.Group.call(this, config);
		this.text = new Kinetic.Text({
			text:'',
        	padding: 5,
	        fill: 'green',
	        align: "left",
        });
        this.rect = new Kinetic.Rect({
            fill: '#ffffdf',
            stroke: '#555',
            strokeWidth: 2,
            padding: 20,
            cornerRadius: 10
        }); 
        this.add(this.rect);
        this.add(this.text);
	},

	setText: function(t){
		this.text.setText(t);
	},

	setWidth: function(w){
		Kinetic.Group.prototype.setWidth.call(this, w);
		this.rect.setWidth(w);
	},

	setHeight: function(h){
		Kinetic.Group.prototype.setHeight.call(this, h);
		this.rect.setHeight(h);
	}

};
Kinetic.Util.extend(Cloudwei.NodeTooltip, Kinetic.Group);