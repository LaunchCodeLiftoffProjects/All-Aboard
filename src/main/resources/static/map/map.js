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
            gametype: data[i].gametype,
            openMembership: data[i].openMembership,
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

    let terrainLayerSource = new ol.source.Stamen({
         layer: 'terrain',
    });

    let labelsLayerSource = new ol.source.Stamen({
        layer: 'terrain-labels',
    })

    var map = new ol.Map({
        target: 'map',
        layers: [
            new ol.layer.Tile({
                source: terrainLayerSource,
            }),
            new ol.layer.Tile({
                source: labelsLayerSource,
            }),
            vectorLayer
        ],
        view: new ol.View({
            center: ol.proj.fromLonLat([-90.1994, 38.6270]),
            zoom: 11.5,
            resolutions: terrainLayerSource.getTileGrid().getResolutions()
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

    let lastHoveredFeature = null;
    map.on('pointermove', function (e) {
      var feature = map.forEachFeatureAtPixel(e.pixel, function (feature) {
        return feature;
      });

      if (feature && lastHoveredFeature !== feature) {
        lastHoveredFeature = feature;
        var coordinates = feature.getGeometry().getCoordinates();
        popup.setPosition(coordinates);
        $(element).popover({
          placement: 'top',
          html: true,
          content: `
            <h3 class='group-name'>${feature.get('name')}</h3>
            <div class='game-type'>Playing <strong>${feature.get('gametype')}</strong></div>
            <div class='open-membership'>${
                feature.get('openMembership') ?
                '<span class="membership-true">Taking new members</span>' :
                '<span class="membership-false">Not taking new members</span>'
            }<div>
           `,
        });
        $(element).popover('show');
      } else if (feature) {
      } else {
        lastHoveredFeature = null;
        $(element).popover('dispose');
      }

      if (e.dragging) {
        $(element).popover('dispose');
        return;
      }
      var pixel = map.getEventPixel(e.originalEvent);
      var hit = map.hasFeatureAtPixel(pixel);
//      map.getTarget().style.cursor = hit ? 'pointer' : '';
    });
}