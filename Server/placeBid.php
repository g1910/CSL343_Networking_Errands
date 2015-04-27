<?php
include 'config.php';

$idAuction=$_GET['idAuction'];
$idBid=$_GET['idBid'];

$con=mysqli_connect($IP,$user,$pass,$db);
//echo "INSERT INTO `Placed`(`idBid`, `idAuction`, `status`) VALUES ($idBid,$idAuction,'P')";
mysqli_query($con,"INSERT INTO `Placed`(`idBid`, `idAuction`, `status`) VALUES ($idBid,$idAuction,'P')")  or die(mysqli_error($con));

	$output=[];
	$arr = array(
		    "Message" => "Success", 
		);
	$output[]=$arr;

//	print(json_encode($arr));
	print(json_encode($output));

?>
