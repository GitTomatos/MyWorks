<div class="container">
	<table class="table info-tbl">
		<tr>
			<th>Country</th>
			<th>City</th>
			<th>Local Time</th>
			<th>Temperature</th>
			<th>Wind speed</th>
			<th>Pressure</th>
			<th>RefreshedAt</th>
		</tr>

		<?php
		$query = "SELECT * FROM `info` ORDER BY `refreshedAt` DESC";
		$info_data = mysqli_query($connect, $query);
		$info_data = mysqli_fetch_all($info_data);
		foreach ($info_data as $data) {
			?>
			<tr>
				<td><?= $data[0] ?></td>
				<td><?= $data[1] ?></td>
				<td><?= $data[2] ?></td>
				<td><?= $data[3] ?></td>
				<td><?= $data[4] ?></td>
				<td><?= $data[5] ?></td>
				<td><?= $data[6] ?></td>
			</tr>
			<?php	
		}
		?>
	</table>
</div>