<?php
include 'config.php';
$location=$_GET['location'];
$endtime=$_GET['endtime'];
$expectedtime=$_GET['expected'];
$description=$_GET['description'];
$userid=$_GET['id_user'];
$con=mysqli_connect($IP,$user,$pass,$db);

date_default_timezone_set("Asia/Kolkata");
$starttime= date( 'Y-m-d H:i:s', time());

mysqli_query($con,"INSERT INTO `Auction`( `location`, `start_time`, `end_time`, `expctd_time`, `description`, `idUser`) VALUES (\"$location\",\"$starttime\",\"$endtime\",\"$expectedtime\",\"$description\",\"$userid\")")  or die(mysqli_error($con));
$arr = array(
    "message" => "Success", 
);
$output[]=$arr;
print(json_encode($output));
?>
