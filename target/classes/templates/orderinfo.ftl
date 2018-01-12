<#assign base = springMacroRequestContext.contextPath />
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>INSPINIA | FooTable</title>

    <link href="${base}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${base}/font-awesome/css/font-awesome.css" rel="stylesheet">

    <!-- FooTable -->
    <link href="${base}/css/plugins/footable/footable.core.css" rel="stylesheet">

    <link href="${base}/css/animate.css" rel="stylesheet">
    <link href="${base}/css/style.css" rel="stylesheet">

</head>

<body>
    <div id="page-wrapper" class="gray-bg">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#">
                        <i class="fa fa-bars"></i>
                    </a>
                    <form role="search" class="navbar-form-custom" action="search_results.html">
                        <div class="form-group">
                            <input type="text" placeholder="Search for something..." class="form-control" name="top-search" id="top-search">
                        </div>
                    </form>
                </div>
            </nav>
        </div>
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>CobarLocal-website</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="index.html">Home</a>
                    </li>
                    <li>
                        <a>Order</a>
                    </li>
                    <li class="active">
                        <strong>OrderList</strong>
                    </li>
                </ol>
            </div>
            <div class="col-lg-2">

            </div>
        </div>
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                    <i class="fa fa-wrench"></i>
                                </a>
                                <ul class="dropdown-menu dropdown-user">
                                    <li>
                                        <a href="#">Config option 1</a>
                                    </li>
                                    <li>
                                        <a href="#">Config option 2</a>
                                    </li>
                                </ul>
                                <a class="close-link">
                                    <i class="fa fa-times"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content" id="app">
                            <table class="footable table table-stripped toggle-arrow-tiny" data-page-size="8">
                                <thead>
                                    <tr>
                                        <th data-toggle="true">Order Number</th>
                                        <th>Table Number</th>
                                        <th>Nick Name</th>
                                        <th>Status</th>
                                        <th>Order Count</th>
                                        <th>Create Time</th>
                                        <th data-hide="all">Order Detail</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <#list orderInfo as order>
                                    <tr>
                                        <td>${order.orderNumber}</td>
                                        <td>${order.tableNumber}</td>
                                        <td>${order.nickName}</td>
                                        <td>${order.status}</td>
                                        <td>${order.count}</td>
                                        <td>${order.creatTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                        <td>
                                            <ul>
                                                <#list order.orderDetail as detail>
                                                    <li>${detail.p_name_en}</li>
                                                    <#list detail.command as commandlist>
                                                        "${commandlist}",</br>
                                                    </#list>
                                                </#list>
                                            </ul>
                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <td colspan="5">
                                            <ul class="pagination pull-right"></ul>
                                        </td>
                                    </tr>
                                </tfoot>
                            </table>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="footer">
            <div class="pull-right">
                10GB of
                <strong>250GB</strong> Free.
            </div>
            <div>
                <strong>Copyright</strong> ROSS 53 Company &copy; 2017-2018
            </div>
        </div>

    </div>
    </div>



    <!-- Mainly scripts -->
    <script src="${base}/js/jquery-2.1.1.js"></script>
    <script src="${base}/js/bootstrap.min.js"></script>
    <script src="${base}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${base}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="${base}/js/vue.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue-resource@1.3.4"></script>

    <!-- FooTable -->
    <script src="${base}/js/plugins/footable/footable.all.min.js"></script>

    <!-- Custom and plugin javascript -->
    <script src="${base}/js/inspinia.js"></script>
    <script src="${base}/js/plugins/pace/pace.min.js"></script>

    <!-- Page-Level Scripts -->
    <script>
        $(document).ready(function () {

            $('.footable').footable();
            $('.footable2').footable();

        });

    </script>

</body>

</html>