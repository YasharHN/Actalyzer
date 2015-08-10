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
    var margin = {top: 20, right: 20, bottom: 30, left: 50},
        width = 960 - margin.left - margin.right,
        height = 500 - margin.top - margin.bottom;

    var parseDate = d3.time.format("%d-%b-%y").parse;

    var x = d3.time.scale()
        .range([0, width]);

    var y = d3.scale.linear()
        .range([height, 0]);

    var xAxis = d3.svg.axis()
        .scale(x)
        .orient("bottom");

    var yAxis = d3.svg.axis()
        .scale(y)
        .orient("left");

    var line = d3.svg.line()
        .x(function(d) { return x(d.label2); })
        .y(function(d) { return y(d.value); });

    var svg = d3.select(divChart).append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");


    dataset.forEach(function(d) {
            d.label2 = d.label2
            d.label2 = +d.label2;
        });

        x.domain(d3.extent(dataset, function(d) { return d.label2; }));
        y.domain(d3.extent(dataset, function(d) { return d.value; }));

        svg.append("g")
            .attr("class", "x axis")
            .attr("transform", "translate(0," + height + ")")
            .call(xAxis);

        svg.append("g")
            .attr("class", "y axis")
            .call(yAxis)
            .append("text")
            .attr("transform", "rotate(-90)")
            .attr("y", 6)
            .attr("dy", ".71em")
            .style("text-anchor", "end")
            .text("Price ($)");

        svg.append("path")
            .datum(dataset)
            .attr("class", "line")
            .attr("d", line);

    return 0;
}
