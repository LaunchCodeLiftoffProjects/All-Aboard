const resultsSection = document.getElementById("results");
let jsonData;
let filteredData;
let view;
let centerPinFeature;
let centerPinSource;
let centerPinLayer;
let gameGroupFeatures = [];
let gamePinSource;
let gamePinLayer;

const gamePinStyle = new ol.style.Style({
    image: new ol.style.Icon({
        scale: 0.1,
        anchor: [0.5, 512],
        anchorXUnits: 'fraction',
        anchorYUnits: 'pixels',
        src: 'map/images/basic-pin.png',
    }),
});
const gamePinStyleHighlighted = new ol.style.Style({
    image: new ol.style.Icon({
        scale: 0.1,
        anchor: [0.5, 512],
        anchorXUnits: 'fraction',
        anchorYUnits: 'pixels',
        src: 'map/images/basic-pin-alt.png',
    }),
});
const centerPinStyle = new ol.style.Style({
    image: new ol.style.Icon({
        scale: 0.1,
        anchor: [0.5, 512],
        anchorXUnits: 'fraction',
        anchorYUnits: 'pixels',
        src: 'map/images/center-pin.png',
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
    jsonData = data;
    filteredData = [...jsonData];
    for (let i = 0; i < filteredData.length; i++) {
        const gameGroup = filteredData[i];
        generateResult(gameGroup);
        generateMapIcon(gameGroup);
    }
    bindEventsToResults();
    generateMap();
}

function bindEventsToResults() {
    $('.result-info').unbind();
    $('.result-info').click(resultOnClick);
    $('.result-info').hover(
        resultOnMouseOver,
        resultOnMouseOut
    );
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

    iconFeature.setStyle(gamePinStyle);
    gameGroupFeatures.push(iconFeature);
}

function generateMap() {
    const terrainLayerSource = new ol.source.Stamen({
         layer: 'terrain',
    });

    const labelsLayerSource = new ol.source.Stamen({
        layer: 'terrain-labels',
    })

    view = new ol.View({
        center: ol.proj.fromLonLat([-90.3246, 38.6126]),
        zoom: 11.5,
        resolutions: terrainLayerSource.getTileGrid().getResolutions()
    });

    centerPinFeature = new ol.Feature({
        geometry: new ol.geom.Point(view.getCenter())
    });
    centerPinFeature.setStyle(new ol.style.Style(null));

    centerPinSource = new ol.source.Vector({
        features: [centerPinFeature],
    });

    centerPinLayer = new ol.layer.Vector({
        source: centerPinSource,
    });

    gamePinSource = new ol.source.Vector({
      features: gameGroupFeatures,
    });

    gamePinLayer = new ol.layer.Vector({
      source: gamePinSource,
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
            gamePinLayer,
            centerPinLayer
        ],
        view: view
    });

    map.addOverlay(popup);

    let lastHoveredFeature = null;
    map.on('pointermove', function (e) {
      let hoveredFeature = map.forEachFeatureAtPixel(e.pixel, function (feature, layer) {
        if (layer === gamePinLayer) {
            return feature;
        }
      });

      gamePinSource.forEachFeature((feature) => {
        if (hoveredFeature !== feature) {
          feature.setStyle(gamePinStyle);
        }
      });

      if (hoveredFeature && lastHoveredFeature !== hoveredFeature) {
        hoveredFeature = moveFeatureUp(hoveredFeature);
        const selectedResult = document.getElementById(`result-${hoveredFeature.get('id')}`);
        selectedResult.classList.add(`selected-result`);
        lastHoveredFeature = hoveredFeature;
        hoveredFeature.setStyle(gamePinStyleHighlighted);
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

      map.hasFeatureAtPixel(pixel);
      map.getTargetElement().style.cursor = hoveredFeature ? 'pointer' : '';
    });

    map.on('click', function(e) {
      let clickedFeature = map.forEachFeatureAtPixel(e.pixel, function (feature, layer) {
        if (layer === gamePinLayer) {
            return feature;
        }
      });
      if (clickedFeature) {
        pinOnClick(clickedFeature);
      }
    })
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

function resultOnClick(e) {
    const resultId = this.id;
    gamePinSource.forEachFeature((feature) => {
        if(resultId === `result-${feature.get('id')}`) {
            view.setCenter(feature.getGeometry().getCoordinates());
            view.setZoom(12.5);
        };
    })
    expandBio(resultId);
}

function pinOnClick(clickedFeature) {
    expandBio(clickedFeature.get('id'));
}

function expandBio(gameGroupId) {
    gameGroupId = Number(gameGroupId.toString().replace('result-', ''));
    $('#results').hide();
    $('#expanded-bio').show();
    let bioData = filteredData.find((elm) => { return gameGroupId === elm.id });
    $('#expanded-bio-name').html(bioData.gameGroupName);
    $('#expanded-bio-description').html(bioData.gameGroupDescription);
    $('#expanded-game-type').html(bioData.gameType);
    $('#expanded-open-membership').html(
        bioData.openMembership ?
        '<span class="expanded-membership-true">Taking new members</span>' :
        '<span class="expanded-membership-false">Not taking new members</span>'
    )
}


function resultOnMouseOver() {
    const resultId = this.id;
    gamePinSource.forEachFeature((feature) => {
        feature.setStyle(gamePinStyle);
        if(resultId === `result-${feature.get('id')}`) {
            feature.setStyle(gamePinStyleHighlighted);
            moveFeatureUp(feature);
        };
    })
}

function resultOnMouseOut() {
    gamePinSource.forEachFeature((feature) => {
        feature.setStyle(gamePinStyle);
    })
}

function searchAddress() {
    let urlEncodedLocation = encodeURIComponent($('#search').val());
    const geocodeURL = `https://nominatim.openstreetmap.org/search.php?q=${urlEncodedLocation}&format=json`;

    $.getJSON(geocodeURL, function(response) {
        const coordinates = response[0];
        view.setCenter(ol.proj.fromLonLat([coordinates.lon, coordinates.lat]));
        centerPinFeature.set(
            'geometry',
            new ol.geom.Point(view.getCenter())
        );
        centerPinFeature.setStyle(centerPinStyle);
    })
}

$('#controls').submit(function(event) {
    event.preventDefault();
    setResultParameters();
});

$('#bio-back-button').click(function(event) {
    $('#results').show();
    $('#expanded-bio').hide();
});

function setResultParameters() {
    gameGroupFeatures = [];
    filteredData = [...jsonData];
    $('.results').empty();

    if($('#search').val() !== '') {
        searchAddress();
    }

    const distanceSelect = Number($('#distance-select').val());
    const viewCenter = ol.proj.toLonLat(view.getCenter());
    const gameSelect = $('#game-select').val();

    if (distanceSelect !== 0) {
        filterGroupsByDistance(viewCenter, distanceSelect);
    }

    if (gameSelect && gameSelect !== '') {
        filterGroupsByGame(gameSelect)
    }

    filteredData.forEach((dataItem) => {
        generateResult(dataItem);
        generateMapIcon(dataItem);
    });

    bindEventsToResults();
    gamePinSource = new ol.source.Vector({
        features: gameGroupFeatures,
    });
    gamePinLayer.setSource(gamePinSource);

    switch(distanceSelect) {
        case 5:
            view.setZoom(12.5);
            break;
        case 10:
            view.setZoom(11.75);
            break;
        case 15:
            view.setZoom(11);
            break;
        case 20:
            view.setZoom(10.25);
            break;
        case 30:
            view.setZoom(9.75);
            break;
        default:
            view.setZoom(12.5);
            break;
    }
}

function filterGroupsByDistance(center, distance) {
    for(let i = filteredData.length - 1; i >= 0; i--) {
        const meterToMiles = 0.00062137119224;
        const featureDistance = new ol.geom.LineString([
            ol.proj.fromLonLat(center),
            ol.proj.fromLonLat(filteredData[i].gameGroupLocation)
        ]).getLength() * meterToMiles;

        if(featureDistance > distance) {
            filteredData.splice(i, 1);
        }
    }
}

function filterGroupsByGame (gameType) {
    for(let i = filteredData.length - 1; i >= 0; i--) {
        if(filteredData[i].gameType !== gameType) {
            filteredData.splice(i, 1);
        }
    }
}

function moveFeatureUp(feature) {
    const featureClone = feature.clone();
    try {
        gamePinLayer.getSource().removeFeature(feature);
    } catch (e) {
        //occasionally fails and throws an error but still functions
        //fine as this just removes unnecessary features
    }
    gamePinLayer.getSource().addFeature(featureClone);
    return featureClone;
}

$('#search').keypress(function(event) {
    if(event.Key === 'Enter') {
        setResultParameters();
    }
});

