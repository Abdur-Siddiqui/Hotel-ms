#!/usr/bin/env bash

spring init \
--boot-version=3.0.2 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=guests-service \
--package-name=com.hotelmanagement.guestsservice \
--groupId=com.hotelmanagement.guestsservice \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
guests-service

spring init \
--boot-version=3.0.2 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=rooms-service \
--package-name=com.hotelmanagement.roomsservice \
--groupId=com.hotelmanagement.roomsservice \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
rooms-service

spring init \
--boot-version=3.0.2 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=reservation-service \
--package-name=com.hotelmanagement.reservationservice\
--groupId=com.hotelmanagement.reservationservice \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
reservation-service

spring init \
--boot-version=3.0.2 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=staff-service \
--package-name=com.hotelmanagement.staffservice \
--groupId=com.hotelmanagement.staffservice \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
staff-service

spring init \
--boot-version=3.0.2 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=api-gateway \
--package-name=com.hotelmanagement.apigateway \
--groupId=com.hotelmanagement.apigateway \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
api-gateway

