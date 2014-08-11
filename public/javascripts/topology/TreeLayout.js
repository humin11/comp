Cloudwei.TreeLayout = function(){
    this.id = "TreeLayout";
};

Cloudwei.TreeLayout.prototype = {

    rootRank: function(nodeArr){
        for(var i = 0; i < nodeArr.length; i++){
            if(nodeArr[i].inLinks.length == 0){
                nodeArr[i].rank = 0;
                this.childRank(nodeArr[i],nodeArr);
            }
        }
    },

    childRank: function(sourceNode,nodeArr){
        for(var i = 0; i < sourceNode.outLinks.length; i++){
            var targetNode = sourceNode.outLinks[i].target;
            targetNode.rank = sourceNode.rank+1;
            for(var j = 0; j < nodeArr.length; j++){
                if(targetNode.getId() == nodeArr[j].getId()){
                    this.childRank(targetNode,nodeArr);
                    break;
                }
            }
        }
    },

    run: function(container,nodes,paddingTop,paddingLeft){
        if(!paddingTop)
            paddingTop = 0;
        if(!paddingLeft)
            paddingLeft = 0;
        var vGap = 50;
        var hGap = 70;
        var rankRow = 0;
        var containerX = container.getX();
        var containerY = container.getY();
        var containerWidth = container.getWidth();
        var rankNodes = [];
        for(var i = 0; i < nodes.length; i++){
            if(nodes[i].isVisible())
                rankNodes.push(nodes[i]);
        }
        this.rootRank(rankNodes);
        rankNodes.sort(function(a,b){
            if(a.rank > b.rank)
                return 1;
            else if(a.rank < b.rank)
                return -1;
            else
                return 0;
        });
        var rankMap = {};
        for(var j = 0; j < rankNodes.length; j++){
            if(rankMap[rankNodes[j].rank])
                rankMap[rankNodes[j].rank].push(rankNodes[j]);
            else
                rankMap[rankNodes[j].rank] = [rankNodes[j]];
        }
        for(var j in rankMap){
            var nodes = rankMap[j];
            var nodesTotalWidth = 0;
            for(var k = 0; k < nodes.length; k++){
                nodesTotalWidth += nodes[k].getWidth();
                nodesTotalWidth += vGap;
            }
            nodesTotalWidth -= vGap;
            var startX = containerX + (containerWidth - nodesTotalWidth) / 2 + paddingLeft;
            var startY = containerY + rankRow * hGap+paddingTop;
            for(var k = 0; k < nodes.length; k++){
                var node = nodes[k];
                node.setPosition({x:startX,y:startY});
                node.innerX = startX - containerX;
                node.innerY = startY - containerY;
                node.updateLinks(false);
                startX += (node.getWidth()+vGap);
            }
            rankRow++;
        }
    }

};
