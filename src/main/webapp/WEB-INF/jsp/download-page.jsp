<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
	<a href="#" data-link="/api/downloadFile/a.jpg">Click Me</a>
	<a href="#" data-link="/api/downloadFile/b.jpg">Click Me</a>


</body>
</html>

<script>
	$("a").click(function(e) {
		e.preventDefault();
		var link = $(this).data('link');
		$.ajax({
			url : link,
			type : 'HEAD',
			error : function() {
				alert("file does not exist!");
			},
			success : function() {
				window.open(link);
			}
		});

	});

</script>