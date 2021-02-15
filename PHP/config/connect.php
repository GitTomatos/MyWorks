<?php 
	$connect = mysqli_connect('localhost', 'root', '', 'weather');

	if (!$connect) {
		die("Error");
	}
?>