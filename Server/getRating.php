<?php

	function getRating($a)
	{
		include 'config.php';
		$con=mysqli_connect($IP,$user,$pass,$db);
//		echo "Select avg(stars) as rating,count(stars) as numRated from `Feedback` group by `idReceiver` having `idReceiver`=$a";
		$result=mysqli_query($con,"Select avg(stars) as rating,count(stars) as numRated from `Feedback` group by `idReceiver` having `idReceiver`=$a") or die(mysqli_error($con));

		$output=[];
		while ($row=mysqli_fetch_assoc($result))
		{
			$output[]=$row;
		}

		return $output;
	}
?>