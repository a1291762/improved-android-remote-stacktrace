<?php

$to_email = "user@example.com";
$from_email = "admin@example.com";

$vars = ['package_name',
    'package_version',
    'phone_model',
    'android_version',
    'stacktrace'];
$count = count($vars);
for ($i = 0; $i < $count; $i++) {
    if ($_POST[$vars[$i]] == "") {
        http_response_code(404);
        die();
    }
}

$pkg_name = $_POST['package_name'];
$pkg_version = $_POST['package_version'];
$model = $_POST['phone_model'];
$and_version = $_POST['android_version'];
$stacktrace = $_POST['stacktrace'];
$date = date(DATE_RFC1036);

//$random = rand(1000,9999);
//$filename = $package."-trace-".$version."-".time()."-".$random;
//$handle = fopen($filename, "w+");
//fwrite($handle, $_POST['stacktrace']);
//fclose($handle);

mail($to_email,
    "Stack trace for ".$pkg_name." ".$pkg_version." on ".$date,
    "Received a stack trace\n".
    "Date ".$date."\n".
    "Package ".$pkg_name." version ".$pkg_version."\n".
    "Phone ".$model." Android version ".$and_version."\n\n".
    $stacktrace,
    "from:".$from_email);

http_response_code(204);
exit(0);

