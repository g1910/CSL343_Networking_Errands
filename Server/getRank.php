<?php

	function getRank($a,$b)
	{
		include 'config.php';
		$con=mysqli_connect($IP,$user,$pass,$db);
//		echo "select count(*)+1 as rank from Placed where `Price`>$b and `idAuction`=$a and `status` =  'A'";
		$result=mysqli_query($con,"select count(*)+1 as rank from Placed where `Price`>$b and `idAuction`=$a and `status` =  'A'") or die(mysqli_error($con));

		$output=[];
		while ($row=mysqli_fetch_assoc($result))
		{
			return $row['rank'];
		}
	}
?>