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

    run: function(diagrammer){
        var vGap = 50;
        var hGap = 70;
        var nodeWidth = 32;
        var rankRow = 1;
        var containerX = diagrammer.getX();
        var containerY = diagrammer.getY();
        var canvasWidth = diagrammer.getWidth();
        var allNodes = diagrammer.nodesLayer.getChildren();
        var rankNodes = [];
        for(var i = 0; i < allNodes.length; i++){
            if(allNodes[i].isVisible())
                rankNodes.push(allNodes[i]);
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
            if(rankMap["rank"+rankNodes[j].rank])
                rankMap["rank"+rankNodes[j].rank].push(rankNodes[j]);
            else
                rankMap["rank"+rankNodes[j].rank] = [rankNodes[j]];
        }
        for(var j in rankMap){
            var nodes = rankMap[j];
            var startX = containerX + (canvasWidth - (nodeWidth + vGap) * nodes.length) / 2 + vGap;
            var startY = containerY + rankRow * hGap;
            for(var k = 0; k < nodes.length; k++){
                var node = nodes[k];
                node.setPosition({x:startX,y:startY});
                node.updateLinks(false);
                startX += (nodeWidth+vGap);
            }
            rankRow++;
        }
    }

};
