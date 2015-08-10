<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Actalyzer - Time Chart</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <link rel="stylesheet" href="http://static.yorksale.com/assets/css/bootstrap.min.css" media="screen">
    <link rel="stylesheet" href="/resources/css/chart.css" media="screen">
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body ng-app="actalyzerApp">

<div class="container" ng-controller="appCtrl">

    <div class="row">
        <div class="col-lg-12">
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="/">
                            Actalyzer
                        </a>
                    </div>

                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav">
                            <li><a href="/spark/pie-chart">Pie Chart</a></li>
                            <li class="active"><a href="/spark/time-chart">Time Chart</a></li>
                        </ul>
                        <form class="navbar-form navbar-left" role="search">
                            <div class="form-group">
                                <input id="filePath" type="text" class="form-control" placeholder="File path"
                                       style="width: 450px"
                                       value="/Users/admin/Projects/big-data-course/project/data/t-data.json">
                            </div>
                            <button id="btnLoadFile" type="button" class="btn btn-default"
                                    ng-click="loadRawData('filePath')">Load Raw Data
                            </button>
                        </form>
                        <ul class="nav navbar-nav navbar-right">
                            <img id="imgLoader" class="hide" src="/resources/img/ajax-loader.gif"/>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
    </div>

    <form name="textForm" ng-submit="" >
        <div class="row">
            <div class="col-lg-3">
                <div class="form-group">
                    <label for="selAttribute">Index:</label>
                    <select class="form-control" id="selAttribute">
                        <option>type</option>
                        <option>language</option>
                        <option>mobileBrowser</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="selAttribute">View:</label>
                    <select class="form-control" id="selView">
                        <option value="month">Month of Year</option>
                        <option value="weekDay">Day of Week</option>
                        <option value="hour">Hour of Day</option>
                    </select>
                </div>
                <div class="form-group">
                    <textarea class="form-control" rows="5" id="txtQuery">SELECT type as label, hour as label2, count(1) as c1 FROM activity group by type,hour</textarea>
                </div>
                <div class="form-group">
                    <button class="btn btn-default" onclick="generateQuery()" type="button">Generate</button>
                    <button class="btn btn-default" ng-click="drawChart()" type="button">Analyze</button>
                </div>
            </div>
            <div class="col-lg-9">
                <div class="form-group">
                    <div id="chart" style="height:500px;"></div>
                </div>
            </div>
        </div>
    </form>

</div>


<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0-alpha1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/underscore.string/2.3.0/underscore.string.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.6/d3.min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.5.1/moment.min.js"></script>
<script src="/resources/js/d3-labels.js"></script>
<script src="/resources/js/chart.js"></script>
<script type="text/javascript">
    /* <![CDATA[ */

    var divChart = '#chart';

    function startLoader(){
        $('#imgLoader').removeClass('hide');
    }

    function stopLoader(){
        $('#imgLoader').addClass('hide');
    }

    var BASE_PIE_CHART_QUERY = "SELECT {0} as label, {1} as label2, count(1) as c1 FROM activity "
            + "group by {0},{1} ";

    $(function() {

    });

    function generateQuery(){
        var attrName = $('#selAttribute').val();
        var selView = $('#selView').val();
        console.log(attrName);
        $('#txtQuery').text( BASE_PIE_CHART_QUERY.format(attrName, selView) );
    }

    angular.module('actalyzerApp', [])
            .controller('appCtrl', ['$scope', '$http', function($scope, $http) {
                $scope.drawChart = function () {
                    startLoader();
                    $http.get('/spark/query?t=TIME&q=' + $('#txtQuery').val()).
                            then(function (response) {
                                stopLoader();
                                drawTimeChart(divChart, response.data);

                            }, function (response) {
                                stopLoader();
                                alert('Cannot execute the query');
                                console.log("response:", response);
                            });
                };

                $scope.loadRawData = function(inputFile){

                    startLoader();
                    var filePath = $('#' + inputFile).val();
                    if(filePath){
                        $http.get('/spark/load?path=' + filePath).
                                then(function(response) {
                                    $('#btnLoadFile').text('Loaded');
                                    $('#btnLoadFile').prop( "disabled", true );
                                    $('#' + inputFile).prop( "disabled", true );
                                    console.log("response:", response);
                                    stopLoader();
                                }, function(response) {
                                    console.log("response:", response);
                                    alert('File path is wrong! Please correct the file path.');
                                    $('#btnLoadFile').text('Load Raw Data');
                                    $('#' + inputFile).focus();
                                    stopLoader();
                                });
                    } else {
                        alert('Please enter your file path');
                        $('#btnLoadFile').text('Load Raw Data');
                        $('#' + inputFile).focus();
                        stopLoader();
                    }
                }
            }]);
    /* ]]> */
</script>
</body>
</html>