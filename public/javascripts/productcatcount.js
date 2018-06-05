var labels = document.getElementById("barchart").getAttribute("data-labels").split(",");
var data = document.getElementById("barchart").getAttribute("data-data").split(",");
var ctx = document.getElementById("prodcatcount").getContext('2d');
var myChart = new Chart(ctx, {
   type: 'bar',
   data: {
       labels: labels,
       datasets: [{
           label: 'Product Category Count',
           data: data,

       }]
   },
   options: {
   responsive: false,
       scales: {
           yAxes: [{
               ticks: {
                   beginAtZero:true
               }
           }]
       }
   }
});