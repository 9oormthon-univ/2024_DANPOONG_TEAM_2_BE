package com.moa.moabackend.store.api.dto.response;

import java.util.List;

public record AddressResDto(
                Meta meta,
                List<Document> documents) {
        public record Meta(
                        int total_count) {
        }

        public record Document(
                        RoadAddress road_address,
                        Address address) {
        }

        public record RoadAddress(
                        String address_name,
                        String region_1depth_name,
                        String region_2depth_name,
                        String region_3depth_name,
                        String road_name,
                        String underground_yn,
                        String main_building_no,
                        String sub_building_no,
                        String building_name,
                        String zone_no) {
        }

        public record Address(
                        String address_name,
                        String region_1depth_name,
                        String region_2depth_name,
                        String region_3depth_name,
                        String mountain_yn,
                        String main_address_no,
                        String sub_address_no,
                        String zip_code) {
        }
}