<?php

session_start();

require_once "config/connect.php";

$api_result = $_SESSION['api_result'];

?>


<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css" integrity="sha384-r4NyP46KrjDleawBgD5tp8Y7UzmLA05oM1iAEQ17CSuDqnUK2+k9luXQOfXJCJ4I" crossorigin="anonymous">
	<link rel="stylesheet" href="./css/styles.css">
	<title>Task 1</title>
</head>
<body>

	<div class="container">
		<p class="text-center mb-4">
			<?php
			if (isset($_SESSION['location']))
				echo "Weather in ". $_SESSION['location'];
			else
				echo "Select a City!";
			?>
		</p>
		<div class="row">
			<table class="table info-tbl mb-6">
				<tr>
					<th>Country</th>
					<th>City</th>
					<th>Local Time</th>
					<th>Temperature</th>
					<th>Wind speed</th>
					<th>Pressure</th>
				</tr>
				<tr>
					<td><?= $api_result['location']['country'] ?></td>
					<td><?= $api_result['location']['name']?></td>
					<td><?= $api_result['location']['localtime']?></td>
					<td><?= $api_result['current']['temperature']?></td>
					<td><?= $api_result['current']['wind_speed']?></td>
					<td><?= $api_result['current']['pressure']?></td>
				</tr>
			</table>
		</div>

		<p class="text-center mb-4">All Cities</p>

		<form method="POST" action="config/btn-handling.php">
			<div class="d-flex justify-content-center my-btn-background">
				<div class="col-2 mr-4 test">
					<button type="submit" class="btn btn-outline-info cust-btn" name="location" value="New York">New York</button>
				</div>

				<div class="col-2 mr-4 test">
					<button type="submit" class="btn btn-outline-info cust-btn" name="location" value="Washington">Washington</button>
				</div>

				<div class="col-2 test">
					<button type="submit" class="btn btn-outline-info cust-btn" name="location" value="Los Angeles">Los Angeles</button>
				</div>

				<div class="col-2 ml-4 test">
					<button type="submit" class="btn btn-outline-info cust-btn" name="location" value="Miami">Miami</button>
				</div>

				<div class="col-2 ml-4 test">
					<button type="submit" class="btn btn-outline-info cust-btn" name="location" value="Seattle">Seattle</button>
				</div>
			</div>
			<div class="btns d-flex btns-columns mt-5">
				<button class="btn btn-info btn-lg mb-4" type="submit" name="save_info">Save</button>
				<button class="btn btn-info btn-lg" type="submit" name="show_info">Show DataBase</button>
			</div>
		</form>


		<?php

		if(!$_SESSION['btn-handling']) {
			$_SESSION['was_shown'] = false;
			$_SESSION['show'] = false;
		}
		$_SESSION['btn-handling'] = false;

		if ($_SESSION['show']) {
			require_once "config/show_db.php";
			$_SESSION['show'] = false;
			$_SESSION['was_shown'] = true;
		}

		?>

	</div>

</body>
</html>