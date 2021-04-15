
/*Chart making */
AmCharts.makeChart("chartdiv",
{
  "type": "serial",
  "categoryField": "category",
  "autoMarginOffset": 40,
  "marginRight": 60,
  "marginTop": 60,
  "colors": ["#ff6158",
"#000000"],
  "startDuration": 1,
  "color": "#000",
  "fontFamily": "Poppins",
  "fontSize": 14,
  "theme": "patterns",
  "categoryAxis": {
    "gridPosition": "start"
  },
  "trendLines": [],
  "graphs": [
    {
      "balloonText": "[[title]] of [[category]]:[[value]]",
      "bullet": "round",
      "bulletSize": 10,
      "id": "AmGraph-1",
      "lineAlpha": 1,
      "lineThickness": 3,
      "title": "Views ",
      "type": "smoothedLine",
      "valueField": "column-1"
    }
  ],
  "guides": [],
  "valueAxes": [
    {
      "id": "ValueAxis-1",
      "title": ""
    }
  ],
  "allLabels": [],
  "balloon": {},
  "titles": [],
  "dataProvider": [
    {
      "category": "January",
      "column-1": 100,
      "column-2": 5
    },
    {
      "category": "February",
      "column-1": "200",
      "column-2": 7
    },
    {
      "category": "March",
      "column-1": "200",
      "column-2": 3
    },
    {
      "category": "April",
      "column-1": "100",
      "column-2": 3
    },
    {
      "category": "May",
      "column-1": "400",
      "column-2": 1
    },
    {
      "category": "June",
      "column-1": "550",
      "column-2": 2
    }
  ]
}
);

/*End Chart making */
