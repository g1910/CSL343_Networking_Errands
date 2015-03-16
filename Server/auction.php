<?php
include 'config.php';
$id=$_POST['id_auction'];
$location=$_POST['location'];
$endtime=$_POST['endtime'];
$expectedtime=$_POST['expected'];
$description=$_POST['description'];
$userid=$_POST['id_user'];
$con=mysqli_connect($IP,$user,$pass,$db);


$starttime= date( 'Y-m-d H:i:s', time());

mysqli_query($con,"INSERT INTO `Auction`(`idAuction`, `location`, `start_time`, `end_time`, `expctd_time`, `description`, `User_idUser`) VALUES (\"$id\",\"$location\",\"$starttime\",\"$endtime\",\"$expectedtime\",\"$description\",\"$userid\")")  or die(mysqli_error($con));

?>
