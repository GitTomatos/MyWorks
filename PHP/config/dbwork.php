<?php 

require_once "connect.php";

$city = $api_result['location']['name'];
$country = $api_result['location']['country'];
$timeLocal = $api_result['location']['localtime'];
$temperature = $api_result['current']['temperature'];
$windSpeed = $api_result['current']['wind_speed'];
$pressure = $api_result['current']['pressure'];
$refreshedAt = date("Y-m-d H:i:s");

$query = "INSERT INTO `info`(`city`,`country`,`timeLocal`,`temperature`,`windSpeed`,`pressure`,`refreshedAt`) VALUES ('$city', '$country', '$timeLocal', '$temperature', '$windSpeed', '$pressure', '$refreshedAt')
		ON DUPLICATE KEY UPDATE `timeLocal`='$timeLocal',`temperature`='$temperature',`windSpeed`='$windSpeed',`pressure`='$pressure',`refreshedAt`='$refreshedAt'";


mysqli_query($connect, $query);

?>