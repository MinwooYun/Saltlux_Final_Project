<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../views/include/header.jsp"></jsp:include>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<script>
	am5.ready(function() {
	
	// Create root element
	// https://www.amcharts.com/docs/v5/getting-started/#Root_element
	var root = am5.Root.new("chartdiv");
	
	
	// Set themes
	// https://www.amcharts.com/docs/v5/concepts/themes/
	root.setThemes([
	  am5themes_Animated.new(root)
	]);
	
	
	// Create chart
	// https://www.amcharts.com/docs/v5/charts/xy-chart/
	var chart = root.container.children.push(am5xy.XYChart.new(root, {
	  panX: true,
	  panY: true,
	  wheelX: "panX",
	  wheelY: "zoomX"
	}));
	
	// Add cursor
	// https://www.amcharts.com/docs/v5/charts/xy-chart/cursor/
	var cursor = chart.set("cursor", am5xy.XYCursor.new(root, {}));
	cursor.lineY.set("visible", false);
	
	
	// Create axes
	// https://www.amcharts.com/docs/v5/charts/xy-chart/axes/
	var xRenderer = am5xy.AxisRendererX.new(root, { minGridDistance: 30 });
	xRenderer.labels.template.setAll({
	  rotation: -90,
	  centerY: am5.p50,
	  centerX: am5.p100,
	  paddingRight: 15
	});
	
	var xAxis = chart.xAxes.push(am5xy.CategoryAxis.new(root, {
	  maxDeviation: 0.3,
	  categoryField: "country",
	  renderer: xRenderer,
	  tooltip: am5.Tooltip.new(root, {})
	}));
	
	var yAxis = chart.yAxes.push(am5xy.ValueAxis.new(root, {
	  maxDeviation: 0.3,
	  renderer: am5xy.AxisRendererY.new(root, {})
	}));
	
	
	// Create series
	// https://www.amcharts.com/docs/v5/charts/xy-chart/series/
	var series = chart.series.push(am5xy.ColumnSeries.new(root, {
	  name: "Series 1",
	  xAxis: xAxis,
	  yAxis: yAxis,
	  valueYField: "value",
	  sequencedInterpolation: true,
	  categoryXField: "country",
	  tooltip: am5.Tooltip.new(root, {
	    labelText:"{valueY}"
	  })
	}));
	
	series.columns.template.setAll({ cornerRadiusTL: 5, cornerRadiusTR: 5 });
	series.columns.template.adapters.add("fill", (fill, target) => {
	  return chart.get("colors").getIndex(series.columns.indexOf(target));
	});
	
	series.columns.template.adapters.add("stroke", (stroke, target) => {
	  return chart.get("colors").getIndex(series.columns.indexOf(target));
	});
	
	
	// Set data
	
	var data = <%= request.getAttribute("data")%>;
	
	
	
	xAxis.data.setAll(data);
	series.data.setAll(data);
	
	
	// Make stuff animate on load
	// https://www.amcharts.com/docs/v5/concepts/animations/
	series.appear(1000);
	chart.appear(1000, 100);
	
	}); // end am5.ready()
	</script>
</head>
<body>
<div id="chartdiv"></div>
</body>
<jsp:include page="../views/include/footer.jsp"></jsp:include>
</html>
