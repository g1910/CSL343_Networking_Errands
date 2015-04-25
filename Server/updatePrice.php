<?php
include 'config.php';


$idBid=$_GET['idBid'];
$idAuction=$_GET['idAuction'];
$price=$_GET['price'];

$con=mysqli_connect($IP,$user,$pass,$db);

date_default_timezone_set("Asia/Kolkata");
$starttime= date( 'Y-m-d H:i:s', time());

//echo "UPDATE `Placed` SET `Price`=$price WHERE `idBid`=$idBid and `idAuction`=$idAuction";
mysqli_query($con,"UPDATE `Placed` SET `Price`=$price WHERE `idBid`=$idBid and `idAuction`=$idAuction")  or die(mysqli_error($con));

	$output=[];
	$arr = array(
		    "Tag" => "Update", 
		);
	$output[]=$arr;

//	print(json_encode($arr));
	print(json_encode($output));

?>
