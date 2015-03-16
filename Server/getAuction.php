<?php
	include 'config.php';
	$user_id=$_GET['user_id'];
	$time=date( 'Y-m-d H:i:s', time());
	$con=mysqli_connect($IP,$user,$pass,$db);
	$result=mysqli_query($con,"select `idAuction`, `location`, `start_time`, `expctd_time`, `description` from `Auction` where `User_idUser`=\"$user_id\" and `end_time`>=\"$time\"") or die("Error: ".mysqli_error($con));
	while ($row=mysqli_fetch_assoc($result))
		$output[]=$row;
	print(json_encode($output));
	mysqli_close($con);
?>