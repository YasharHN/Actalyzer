<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Actalyzer</title>
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
              <li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li>
              <li><a href="#">Link</a></li>
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Dropdown <span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="#">Action</a></li>
                  <li><a href="#">Another action</a></li>
                  <li><a href="#">Something else here</a></li>
                  <li class="divider"></li>
                  <li><a href="#">Separated link</a></li>
                  <li class="divider"></li>
                  <li><a href="#">One more separated link</a></li>
                </ul>
              </li>
            </ul>
            <form class="navbar-form navbar-left" role="search">
              <div class="form-group">
                <input type="text" class="form-control" placeholder="Search">
              </div>
              <button type="submit" class="btn btn-default">Submit</button>
            </form>
            <ul class="nav navbar-nav navbar-right">
              <li><a href="#">Link</a></li>
            </ul>
          </div>
        </div>
      </nav>
    </div>
  </div>


  <form name="textForm" ng-submit="" >
  <div class="row">
    <div class="col-lg-4">
      <div class="form-group">
        <textarea class="form-control" rows="5" id="txtQuery">SELECT username, count(1) FROM activity group by username order by c1 desc</textarea>
      </div>
      <div class="form-group">
        <button class="btn btn-default" ng-click="drawChart()" type="button">Go</button>
      </div>
    </div>
    <div class="col-lg-8">
      <div class="form-group">
        <div id="chart" style="height:400px;" data-drilldown-destination="filelist_by_category"
             data-drilldown-key="atime" ></div>
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
<script src="/resources/js/d3-labels.js"></script>
<script src="/resources/js/chart.js"></script>
<script type="text/javascript">
  /* <![CDATA[ */

  var divChart = '#chart';

  angular.module('actalyzerApp', [])
    .controller('appCtrl', ['$scope', '$http', function($scope, $http) {

      $scope.drawChart = function(){

        $http.get('/spark/query?q=' + $('#txtQuery').val()).
                then(function(response) {
                  //console.log("response:", response);
                  drawPieChart(divChart, response.data);
                }, function(response) {
                  console.log("response:", response);
                });
      }

    }]);
  /* ]]> */
</script>
</body>
</html>