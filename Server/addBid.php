<?php
include 'config.php';

$idAuction=$_GET['idAuction'];
$id_user=$_GET['id_user'];
$location = $_GET['location'];
$desc = $_GET['desc'];
$order= json_decode($_GET['order']);

$con=mysqli_connect($IP,$user,$pass,$db);

//echo "INSERT INTO `Bid`( `location`, `idUser`) VALUES (\"$location\",$id_user)";
mysqli_query($con,"INSERT INTO `Bid`( `location`, `idUser`) VALUES (\"$location\",$id_user)")  or die(mysqli_error($con));

//echo "Select `idBid` from  `Bid` where `location`=\"$location\"  and `idUser` = $id_user";
$result =mysqli_query($con,"Select `idBid` from  `Bid` where `location`=\"$location\"  and `idUser` = $id_user")  or die(mysqli_error($con));

while ($row=mysqli_fetch_assoc($result))
{
	$idBid=$row['idBid'];
}

//echo "INSERT INTO `Placed`(`idBid`, `idAuction`, `status`) VALUES ($idBid,$idAuction,'P')";
mysqli_query($con,"INSERT INTO `Placed`(`idBid`, `idAuction`, `status`) VALUES ($idBid,$idAuction,'P')")  or die(mysqli_error($con));

for ($i=0;$i<count($order);$i++)
{
	$temp=$order[$i];
	$item= $temp->item;
	$price_per_item = $temp->price_per_item;
	$quantity = $temp->quantity;
//	echo "INSERT INTO `Order`(`item`, `quantity`, `price_per_item`, `idBid`) VALUES (\"$item\",$quantity,$price_per_item,$idBid)";
	mysqli_query($con,"INSERT INTO `Order`(`item`, `quantity`, `price_per_item`, `idBid`) VALUES (\"$item\",$quantity,$price_per_item,$idBid)") or die(mysqli_error($con));
}

//echo $idBid;

	$output=[];
	$arr = array(
		    "Message" => "Success", 
		);
	$output[]=$arr;

//	print(json_encode($arr));
	print(json_encode($output));

?>
