<template>
  <div>
    <div id="container" style="width: 100%;height:100%"></div>
  </div>
</template>

<script>
let map;
export default {
  name: "Map",
  data() {
    return {

    }
  },
  mounted() {

    // 百度地图API功能
    map = new BMap.Map('container'); // 创建Map实例
    map.centerAndZoom(new BMap.Point(116.404, 39.915), 12); // 初始化地图,设置中心点坐标和地图级别
    map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放

    var geolocation = new BMap.Geolocation();
    geolocation.getCurrentPosition(function(r){
      if(this.getStatus() == BMAP_STATUS_SUCCESS){
        var mk = new BMap.Marker(r.point);
        map.addOverlay(mk);
        map.panTo(r.point);
        // alert('您的位置：'+r.point.lng+','+r.point.lat);
      }
      else {
        // alert('failed'+this.getStatus());
      }
    });


  }
}

</script>

<style scoped>

</style>
