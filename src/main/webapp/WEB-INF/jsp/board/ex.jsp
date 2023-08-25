<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>jQuery UI Dialog Example</title>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>

<a href="#" class="showPage">Open Dialog</a>

<div id="dialog" title="Sample Dialog">
    <p>This is a sample dialog content.</p>
</div>

<script>
    $(document).ready(function() {
        $("#dialog").dialog({
            autoOpen: false,
            modal: true,
            width: 400,
            buttons: {
                "Close": function() {
                    $(this).dialog("close");
                }
            }
        });

        $(".showPage").on("click", function(e) {
            e.preventDefault(); // Prevent default link behavior
            $("#dialog").dialog("open");
        });
    });
</script>

</body>
</html>
