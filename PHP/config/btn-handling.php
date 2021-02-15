<?php
session_start();

$api_result = $_SESSION['api_result'];
$_SESSION['btn-handling'] = true;


if(isset($_POST['show_info'])){

	if ($_SESSION['was_shown']) {
		$_SESSION['show'] = false;
		$_SESSION['was_shown'] = false;
	} else {
		$_SESSION['show'] = true;
	}

} elseif (isset($_POST['save_info'])){

	$api_result = $_SESSION['api_result'];

	if ($api_result){
		require_once "dbwork.php";
	}

	if ($_SESSION['was_shown'])
		$_SESSION['show'] = true;

} else {
	$_SESSION['was_shown'] = false;
}


$is_selected = false;

if (isset($_POST['location'])) {

	$is_selected = true;
	$location = "{$_POST['location']}";
	$_SESSION['location'] = $location;

}

if ($is_selected) {
	$queryString = http_build_query([
		'access_key' => 'bcd91eba085cc8e6fc57cd0c6b1adcf7',
		'query' => $location,
	]);

	$ch = curl_init(sprintf('%s?%s', 'http://api.weatherstack.com/current', $queryString));
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

	$json = curl_exec($ch);
	curl_close($ch);

	$api_result = json_decode($json, true);

	$_SESSION['api_result'] = $api_result;
}


header("Location: ../index.php");
?>