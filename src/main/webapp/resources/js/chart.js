/**
 * Created by Yashar on 2015-08-08.
 */
function drawPieChart(divChart, dataset){
    $(divChart + " svg").remove();
    // compute total
    tot = 0;
    dataset.forEach(function(e){ tot += e.value; });
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
            return (dataset[i].value / tot ) * 100 > 10 ? ((dataset[i].value / tot ) * 100).toFixed(1) + "%" : "";
        }
    ).attr("fill","#fff")
        .classed("slice-label",true);

    legend = svgParent.append("g")
        .attr("class","legend")
        .attr("transform","translate(50,50)")
        .style("font-size","12px")
        .call(d3.legend);

}
