(function ($) {

    var LocalJobs = {};
    window.LocalJobs = LocalJobs;

    var template = function (name) {
        return Mustache.compile($('#' + name + '-template').html());
    };

    const  map = L.map('map').setView([0, 0], 13);
    marker = L.marker([0, 0]).addTo(map);
    var geo = document.getElementById("map");
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);
    marker.setLatLng([0, 0], 13).bindPopup('A pretty CSS3 popup.<br> Easily customizable.')
            .openPopup();
    LocalJobs.HomeView = Backbone.View.extend({
        tagName: "form",
        el: $("#main"),

        events: {
            "submit": "findJobs"
        },

        render: function () {
            console.log("rendering home page..");
            $("#results").empty();

            return this;
        },

        findJobs: function (event) {
            console.log('in findJobs()...');
            event.preventDefault();
            $("#results").empty();
            $("#jobSearchForm").mask("Finding Jobs ...");
            var skills = this.$('input[name=skills]').val().split(',');

            console.log("skills : " + skills);

            var self = this;

            navigator.geolocation.getCurrentPosition(function (position) {
                var longitude = position.coords.longitude;
                var latitude = position.coords.latitude;
                console.log('longitude .. ' + longitude);
                console.log('latitude .. ' + latitude);


                $("#jobSearchForm").unmask();

                var latlong = [latitude, longitude];

                self.plotUserLocation(latlong, map);


                $.get("api/jobs/" + skills + "/?longitude=" + longitude + "&latitude=" + latitude, function (results) {
                    $("#jobSearchForm").unmask();
                    self.renderResults(results, self, map);
                });
            }, 
            function (e) {
                $("#jobSearchForm").unmask();
                switch (e.code) {
                    case e.PERMISSION_DENIED:
                        alert('You have denied access to your position. You will ' +
                                'not get the most out of the application now.');
                        break;
                    case e.POSITION_UNAVAILABLE:
                        alert('There was a problem getting your position.');
                        break;
                    case e.TIMEOUT:
                        alert('The application has timed out attempting to get ' +
                                'your location.');
                        break;
                    default:
                        alert('There was a horrible Geolocation error that has ' +
                                'not been defined.');
                }
            },
                    {timeout: 45000}

            );

        },
//////////////////////////////////////////////////////////////////////////////////tagheer

        plotUserLocation: function (latLng, map) {
            marker.setLatLng(latLng, 13).bindPopup('you are here')
                    .openPopup();
        },

        renderResults: function (results, self, map) {
            var infoWindow = L.marker([0, 0]);            
            jQuery.each(results,function (i, val) {
                self.renderJob(val, map, infoWindow);
            });           
        },

        renderJob: function (result, map, infoWindow) {
            result.marker = L.marker([result.latitude, result.longitude],13).addTo(map);
            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            }).addTo(map);            
            marker.setLatLng([result.latitude, result.longitude],13).bindPopup('here')
                    .openPopup();            
        },

        jobInfo: function (job) {
            var text = '';
            text += '<div class="job_info">';
            text += '<h3>' + job['jobTitle'] + '</h3>';
            text += '<p>' + job['formattedAddress'] + '</p>';
            text += '<p>' + job['companyName'] + '</p>';
            text += '<p>' + job['skills'] + '</p>';
            text += '<p>' + job['distance'] + ' KM</p>';
            return text;
        }

    });

////////////////////////////////////////////////////////////////////////////////
    LocalJobs.JobView = Backbone.View.extend({
        template: template("job"),
        initialize: function (options) {
            this.result = options.result;
        },

        render: function () {
            this.$el.html(this.template(this));
            return this;
        },
        jobtitle: function () {
            return this.result['jobTitle'];
        },
        address: function () {
            return this.result['formattedAddress'];
        },
        skills: function () {
            return this.result['skills'];
        },
        company: function () {
            return this.result['companyName'];
        },
        distance: function () {
            return this.result['distance'] + " KM";
        }
    });


    LocalJobs.Router = Backbone.Router.extend({
        el: $("#main"),

        routes: {
            "": "showHomePage"
        },
        showHomePage: function () {
            console.log('in home page...');
            var homeView = new LocalJobs.HomeView();
            this.el.append(homeView.render().el);
        }

    });

    var app = new LocalJobs.Router();
    Backbone.history.start();

})(jQuery);