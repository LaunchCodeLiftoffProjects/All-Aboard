fetch('map/example-map-data.json').then((response) => {
    response.json().then((data) => {
        generateMap(data);
    });
});

function generateMap(data) {
    let mapIcons = [];

    for (let i = 0; i < data.length; i++) {
        var iconFeature = new ol.Feature({
            geometry: new ol.geom.Point(ol.proj.fromLonLat(
                data[i].coordinates
            )),
            name: data[i].name,
        });

        var iconStyle = new ol.style.Style({
          image: new ol.style.Icon({
            scale: 0.1,
            anchor: [0.5, 512],
            anchorXUnits: 'fraction',
            anchorYUnits: 'pixels',
            src: 'map/images/basic-pin.png',
          }),
        });

        iconFeature.setStyle(iconStyle);
        mapIcons.push(iconFeature);
    }

    var vectorSource = new ol.source.Vector({
      features: mapIcons,
    });

    var vectorLayer = new ol.layer.Vector({
      source: vectorSource,
    });

    var map = new ol.Map({
        target: 'map',
        layers: [
            new ol.layer.Tile({
                source: new ol.source.Stamen({
                    layer: 'watercolor',
                }),
            }),
            new ol.layer.Tile({
                source: new ol.source.Stamen({
                    layer: 'terrain-labels',
                }),
            }),
            vectorLayer
        ],
        view: new ol.View({
            center: ol.proj.fromLonLat([37.41, 8.82]),
            zoom: 4
        })
    });

    var element = document.getElementById('popup');

    var popup = new ol.Overlay({
      element: element,
      positioning: 'bottom-center',
      stopEvent: false,
      offset: [0, -50],
    });
    map.addOverlay(popup);


    map.on('click', function (evt) {
      var feature = map.forEachFeatureAtPixel(evt.pixel, function (feature) {
        return feature;
      });
      if (feature) {
        $(element).popover('dispose');
        var coordinates = feature.getGeometry().getCoordinates();
        popup.setPosition(coordinates);
        $(element).popover({
          placement: 'top',
          html: true,
          content: feature.get('name'),
        });
        $(element).popover('show');
      } else {
        $(element).popover('dispose');
      }
    });

    map.on('pointermove', function (e) {
      if (e.dragging) {
        $(element).popover('dispose');
        return;
      }
      var pixel = map.getEventPixel(e.originalEvent);
      var hit = map.hasFeatureAtPixel(pixel);
      map.getTarget().style.cursor = hit ? 'pointer' : '';
    });
}