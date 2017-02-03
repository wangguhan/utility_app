#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Feb  2 20:51:15 2017

@author: CHIAMAO_SHIH
"""

import fiona 
import shapely.geometry


kenya_shape = fiona.open("Kenya_sublocations/kenya_sublocations.shp")

def region_check(points_list):
    count = 0
    for point in points_list:
        this_point = shapely.geometry.Point(point) 
        for shapefile_rec in kenya_shape:
            if(shapely.geometry.asShape(shapefile_rec['geometry']).contains(this_point)):
                print("Region found")
                print("Point %s is in region with id =" %(count) ,shapefile_rec['id'], ".")
                ##print(shapefile_rec['geometry'])
                break;
            if(shapefile_rec['id'] == str(len(kenya_shape)-1)):
                print("Point %s is not in Kenya." %(count))
        count += 1;

if __name__ == '__main__':
    ##(lat, long) of Nairobi = (1.2921° S, 36.8219° E)
    ##longtitude goes first
    points_list = [(36.8219, -1.2921),(-1.2920, 36.8218), (500, 500)]
    region_check(points_list);