const resultsSection = document.getElementById("results");
const mapIcons = [];
let vectorSource;
let view;

const iconStyle = new ol.style.Style({
    image: new ol.style.Icon({
        scale: 0.1,
        anchor: [0.5, 512],
        anchorXUnits: 'fraction',
        anchorYUnits: 'pixels',
        src: 'map/images/basic-pin.png',
    }),
});
const iconStyleHighlighted = new ol.style.Style({
    image: new ol.style.Icon({
        scale: 0.1,
        anchor: [0.5, 512],
        anchorXUnits: 'fraction',
        anchorYUnits: 'pixels',
        src: 'map/images/basic-pin-alt.png',
    }),
});
const popupElm = document.getElementById('popup')
const popup = new ol.Overlay({
    element: popupElm,
    positioning: 'bottom-center',
    stopEvent: false,
    offset: [0, -50],
});

fetch('map/example-map-data.json').then((response) => {
    response.json().then(mapDataFetchAction);
});


function mapDataFetchAction(data) {
    for (let i = 0; i < data.length; i++) {
        const gameGroup = data[i];
        generateResult(gameGroup);
        generateMapIcon(gameGroup);
    }
    $('.result-info').click(resultOnClick);
    $('.result-info').hover(
        resultOnMouseOver,
        resultOnMouseOut
    );
    generateMap();
}

function generateResult(gameGroup) {
    resultsSection.innerHTML += `
        <div class='result-info' id='result-${gameGroup.id}'>
            <h3 class='group-name-panel'>${gameGroup.gameGroupName}</h3>
            <div class='game-type-panel'>Playing <strong>${gameGroup.gameType}</strong></div>
            <div class='open-membership-panel'>${
                gameGroup.openMembership ?
                '<span class="membership-true">Taking new members</span>' :
                '<span class="membership-false">Not taking new members</span>'
            }</div>
        </div>
    `;
}

function generateMapIcon(gameGroup) {
    const iconFeature = new ol.Feature({
        geometry: new ol.geom.Point(ol.proj.fromLonLat(
            gameGroup.gameGroupLocation
        )),
        gameGroupName: gameGroup.gameGroupName,
        gameType: gameGroup.gameType,
        openMembership: gameGroup.openMembership,
        gameGroupDescription: gameGroup.gameGroupDescription,
        id: gameGroup.id,
    });

    iconFeature.setStyle(iconStyle);
    mapIcons.push(iconFeature);
}

function generateMap() {
    vectorSource = new ol.source.Vector({
      features: mapIcons,
    });

    const vectorLayer = new ol.layer.Vector({
      source: vectorSource,
    });

    const terrainLayerSource = new ol.source.Stamen({
         layer: 'terrain',
    });

    const labelsLayerSource = new ol.source.Stamen({
        layer: 'terrain-labels',
    })

    view = new ol.View({
        center: ol.proj.fromLonLat([-90.1994, 38.6270]),
        zoom: 11.5,
        resolutions: terrainLayerSource.getTileGrid().getResolutions()
    });

    const map = new ol.Map({
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
        view: view
    });

    map.addOverlay(popup);

    let lastHoveredFeature = null;
    map.on('pointermove', function (e) {
      const hoveredFeature = map.forEachFeatureAtPixel(e.pixel, function (feature) {
        return feature;
      });

      vectorSource.forEachFeature((feature) => {
        if (hoveredFeature !== feature) {
          feature.setStyle(iconStyle);
        }
      });

      if (hoveredFeature && lastHoveredFeature !== hoveredFeature) {
        const selectedResult = document.getElementById(`result-${hoveredFeature.get('id')}`);
        selectedResult.classList.add(`selected-result`);
        lastHoveredFeature = hoveredFeature;
        hoveredFeature.setStyle(iconStyleHighlighted);
        showPinPopup(hoveredFeature);
      } else if (!hoveredFeature) {
        lastHoveredFeature = null;
        const resultElms = document.querySelectorAll(`.result-info`).forEach((resultElm) => {
           resultElm.classList.remove(`selected-result`);
        });
        hidePinPopup();
      }

      if (e.dragging) {
        hidePinPopup();
        return;
      }
      const pixel = map.getEventPixel(e.originalEvent);
      const hit = map.hasFeatureAtPixel(pixel);
      map.getTargetElement().style.cursor = hit ? 'pointer' : '';
    });
}

function showPinPopup(feature) {
    const coordinates = feature.getGeometry().getCoordinates();
    popup.setPosition(coordinates);
    $(popupElm).popover({
      placement: 'top',
      html: true,
      content: `
        <h3 class='group-name'>${feature.get('gameGroupName')}</h3>
        <div class='game-type'>Playing <strong>${feature.get('gameType')}</strong></div>
        <div class='open-membership'>${
           feature.get('openMembership') ?
           '<span class="membership-true">Taking new members</span>' :
           '<span class="membership-false">Not taking new members</span>'
        }<div>
       `,
    });
    $(popupElm).popover('show');
}

function hidePinPopup(feature) {
    $(popupElm).popover('dispose');
}

function resultOnClick() {
    const resultId = this.id;
    vectorSource.forEachFeature((feature) => {
        if(resultId === `result-${feature.get('id')}`) {
            view.setCenter(feature.getGeometry().getCoordinates());
            view.setZoom(11.5)
        };
    })

}

function resultOnMouseOver() {
    const resultId = this.id;
    vectorSource.forEachFeature((feature) => {
        feature.setStyle(iconStyle);
        if(resultId === `result-${feature.get('id')}`) {
            feature.setStyle(iconStyleHighlighted);
        };
    })
}

function resultOnMouseOut() {
    vectorSource.forEachFeature((feature) => {
        feature.setStyle(iconStyle);
    })
}

function searchAddress() {
    let input = encodeURIComponent($('#search').val());
    const geocodeURL = `https://geocoding.geo.census.gov/geocoder/geographies/onelineaddress?address=${input}&benchmark=4&vintage=Current_Current&format=json&callback=?`;

    $.getJSON(geocodeURL, function(response) {
        const coordinates = response.result.addressMatches[0].coordinates;
        view.setCenter(ol.proj.fromLonLat([coordinates.x, coordinates.y]));
        view.setZoom(13.5);
    })
}

$('#controls').submit(function(event) {
    event.preventDefault();
    searchAddress();
});