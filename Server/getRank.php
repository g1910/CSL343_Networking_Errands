<?php
	function getRank($a,$b,$c)
	{
		include 'config.php';
		$con=mysqli_connect($IP,$user,$pass,$db);
//		echo "select `idCategory` from Placed where `idBid`=$c and `idAuction`=$a";
//		echo "select `idCategory` from Placed where `idBid`=$c and `idAuction`=$a";
//echo "select `idCategory` from Placed where `idBid`=$c and `idAuction`=$a";
//    	$result=mysqli_query($con,"select `idCategory` from Placed where `idBid`=$c and `idAuction`=$a") or die(mysqli_error($con));
		$result=mysqli_query($con,"select `idCategory` from Placed where `idBid`=$c and `idAuction`=$a") or die(mysqli_error($con));
//		echo "select count(*)+1 as rank from Placed where `Price`>$b and `idAuction`=$a and `status` =  'A'";
		
//		echo json_encode($result);
//		echo mysqli_num_rows($result);
		$row=mysqli_fetch_assoc($result);
		$c=$row['idCategory'];
//        echo $c.'sdf';
        if (empty($c))
        {
            return 0;
        }
//		echo "select count(*)+1 as rank from Placed where `Price`>$b and `idCategory`=$c and `idAuction`=$a and `status` =  'A'";
//echo "select count(*)+1 as rank from Placed where `Price`>$b and `idCategory`=$c and `idAuction`=$a and `status` =  'A'";
		$result=mysqli_query($con,"select count(*)+1 as rank from Placed where `Price`>$b and `idCategory`=$c and `idAuction`=$a and `status` =  'A'") or die("Error".mysqli_error($con));
		$output=[];
		while ($row=mysqli_fetch_assoc($result))
		{
			return $row['rank'];
		}
	}
?>
