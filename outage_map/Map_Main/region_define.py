#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Feb  2 20:51:15 2017

@author: CHIAMAO_SHIH
"""

import fiona 
import shapely.geometry
from google_map_plotter import GoogleMapPlotter

kenya_subloc = fiona.open("../Kenya_Shapefiles/5th_level_sublocations/kenya_sublocations.shp") # num = 3715
#kenya_loc = fiona.open("../Kenya_Shapefiles/4th_level_locations/kenya_locations.shp") # num = 1143
#kenya_divisions = fiona.open("../Kenya_Shapefiles/3rd_level_divisions_II/kenya_divisions.shp") # num = 300
#kenya_county = fiona.open("../Kenya_Shapefiles/County/County.shp") # num = 47

def region_check(area_list, shpfile):
    points_list = []
    id_list = []
    for area in area_list:
        try:
            points_list.append(GoogleMapPlotter.geocode(area)) ##(lat, lng)
            print("The coordinates of ", area, "is: ",points_list[-1])
        except IndexError:
            points_list.append((37.8718992,-122.2607286)) ##if not found, gives it coordinates of UC Berkeley
            print("*****************************************************")
            print("ATTENTION: The coordinates of ", area, "is not found.")
            print("*****************************************************")
    count = 0
    for point in points_list:
        this_point = shapely.geometry.Point(point[1],point[0])
        ##SWAP the coordinates!!!!!!!!
        ##Longtitude goes first in shapely
        for shapefile_rec in shpfile:
            if(shapely.geometry.asShape(shapefile_rec['geometry']).contains(this_point)):
                print("%s is in Kenya with region id =" %(area_list[count]) ,shapefile_rec['id'], ".")
                id_list.append(shapefile_rec['id'])
                points_num = 0
                for lat_and_long in shapefile_rec['geometry']['coordinates'][0]:
                    points_num += 1
                #print("number of points is: ", points_num)
                break;
            if(shapefile_rec['id'] == str(len(shpfile)-1)):
                print("%s is not in Kenya." %(area_list[count]))
        count += 1;
    print("id_list: ", id_list)
    return id_list

if __name__ == '__main__':
    area_list = ["Nairobi", "UC Berkeley", "Bamburi", "Mazeras, Coast Region, Kenya", "aaappelsdk"]
    region_check(area_list, kenya_subloc);