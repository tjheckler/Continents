package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class ChartDemoController extends Controller

{
    public Result getChart()
    {
        return ok(views.html.chartdemo.render());
    }
    public Result getBarChart()
    {
        return ok(views.html.barchart.render());
    }
}
