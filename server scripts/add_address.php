<?php
 


 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();

$response = array();


 $email = $_POST['email'];
 $address = $_POST['address'];

 $res = mysql_query("UPDATE User SET address='$address' WHERE emailId='$email'")or die(mysql_error()) ;
 
 if (mysql_affected_rows() != 0) {

    echo "successfully updated";
}


?>