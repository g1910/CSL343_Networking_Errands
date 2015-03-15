<?php
include 'config.php';
$id=$_GET['id_auction'];
$location=$_GET['location'];
//$starttime=$_GET['starttime'];
$endtime=$_GET['endtime'];
$expectedtime=$_GET['expected'];
$description=$_GET['description'];
$userid=$_GET['id_user'];
$con=mysqli_connect($IP,$user,$pass,$db);
echo time();
$starttime= date( 'Y-m-d H:i:s', time());
echo "INSERT INTO `Auction`(`idAuction`, `location`, `start_time`, `end_time`, `expctd_time`, `description`, `User_idUser`) VALUES (\"$id\",\"$location\",\"$starttime\",\"$endtime\",\"$expectedtime\",\"$description\",\"$userid\",)";
mysqli_query($con,"INSERT INTO `Auction`(`idAuction`, `location`, `start_time`, `end_time`, `expctd_time`, `description`, `User_idUser`) VALUES (\"$id\",\"$location\",\"$starttime\",\"$endtime\",\"$expectedtime\",\"$description\",\"$userid\")")  or die(mysqli_error($con));

?>
