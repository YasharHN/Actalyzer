/**
 * Created by Yashar on 2015-08-08.
 */

if (!String.prototype.format) {
    String.prototype.format = function() {
        var args = arguments;
        return this.replace(/{(\d+)}/g, function(match, number) {
            return typeof args[number] != 'undefined'
                ? args[number]
                : match
                ;
        });
    };
}

function drawPieChart(divChart, dataset){
    $(divChart + " svg").remove();
    // compute total
    total = 0;
    dataset.forEach(function(e){ total += e.value; });
    var w = $(divChart).width();
    var h = $(divChart).height();
    var r = Math.min(w, h) /2;

    var color = d3.scale.category10();
    var vis = d3.select(divChart)
        .append("svg:svg")
        .attr("data-chart-context", divChart).data([dataset])
        .attr("width", w).attr("height", h).append("svg:g")
        .attr("transform", "translate(" + (w - r) + "," + r + ")");
    var svgParent = d3.select("svg[data-chart-context='" + divChart + "']");
    var pie = d3.layout.pie().value(function(d){return d.value;});
    var arc = d3.svg.arc().outerRadius(r);
    var arcs = vis.selectAll("g.slice").data(pie).enter().append("svg:g").attr("class","slice");
    arcs.append("svg:path")
        .attr("fill", function(d, i) {
            return color(i);
        })
        .attr("stroke", "#fff")
        .attr("stroke-width", "1")
        .attr("d", function(d) {
            //console.log(arc(d));
            return arc(d);
        })
        .attr("data-legend",function(d) { return '[ ' + d.value + ' ] - ' + d.data.label; })
        .attr("data-legend-pos",function(d) { return d.data.pos; })
        .classed("slice",true)
        .append("svg:title")
        .text(function(d) { return d.data.label; });

    arcs.append("svg:text").attr("transform", function(d){
        d.innerRadius = 0;
        d.outerRadius = r;
        return "translate(" + arc.centroid(d) + ")";}).attr("text-anchor", "middle").text( function(d, i) {
            return (dataset[i].value / total ) * 100 > 10 ? ((dataset[i].value / total ) * 100).toFixed(1) + "%" : "";
        }
    ).attr("fill","#fff")
        .classed("slice-label",true);

    legend = svgParent.append("g")
        .attr("class","legend")
        .attr("transform","translate(50,50)")
        .style("font-size","12px")
        .call(d3.legend);
    return total;

}

function drawTimeChart(divChart, dataset){
    $(divChart + " svg").remove();
    var dataGroup = d3.nest()
        .key(function(d) {return d.label;})
        .entries(dataset);
    console.log(JSON.stringify(dataGroup));
    var color = d3.scale.category20b();
    //var vis = d3.select(divChart),
    var vis = d3.select(divChart)
        .append("svg:svg")
        .attr("width", 800).attr("height", 500),
        WIDTH = 800,
        HEIGHT = 500,
        MARGINS = {
            top: 50,
            right: 20,
            bottom: 50,
            left: 50
        },
        lSpace = WIDTH/dataGroup.length;
    xScale = d3.scale.linear().range([MARGINS.left, WIDTH - MARGINS.right]).domain([d3.min(dataset, function(d) {
        return d.label2;
    }), d3.max(dataset, function(d) {
        return d.label2;
    })]),
        yScale = d3.scale.linear().range([HEIGHT - MARGINS.top, MARGINS.bottom]).domain([d3.min(dataset, function(d) {
            return d.value;
        }), d3.max(dataset, function(d) {
            return d.value;
        })]),
        xAxis = d3.svg.axis()
            .scale(xScale),
        yAxis = d3.svg.axis()
            .scale(yScale)
            .orient("left");

    vis.append("svg:g")
        .attr("class", "x axis")
        .attr("transform", "translate(0," + (HEIGHT - MARGINS.bottom) + ")")
        .call(xAxis);
    vis.append("svg:g")
        .attr("class", "y axis")
        .attr("transform", "translate(" + (MARGINS.left) + ",0)")
        .call(yAxis);

    var lineGen = d3.svg.line()
        .x(function(d) {
            return xScale(d.label2);
        })
        .y(function(d) {
            return yScale(d.value);
        })
        .interpolate("basis");
    dataGroup.forEach(function(d,i) {
        vis.append('svg:path')
            .attr('d', lineGen(d.values))
            .attr('stroke', function(d,j) {
                return "hsl(" + Math.random() * 360 + ",100%,50%)";
            })
            .attr('stroke-width', 2)
            .attr('id', 'line_'+d.key)
            .attr('fill', 'none');

        vis.append("text")
            .attr("x", (lSpace/2)+i*lSpace)
            .attr("y", HEIGHT)
            .style("fill", "black")
            .attr("class","legend")
            .on('click',function(){
                var active   = d.active ? false : true;
                var w = active ? 4 : 2;
                d3.select("#line_" + d.key)
                    .attr('stroke-width', w)
                    //.style("opacity", opacity);
                d.active = active;
                console.log(d);
            })
            .text(d.key);
    });


    return 0;
}
