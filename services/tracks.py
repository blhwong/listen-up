import json
from flask import Flask
from werkzeug.exceptions import NotFound

from services import jsonify, root

app = Flask(__name__)

with open('{}/services/data/tracks.json'.format(root()), 'r') as f:
    tracks = json.load(f)


@app.route('/', methods=['GET'])
@jsonify
def track_index():
    return {
        'uri': '/',
        'subresource_uris': {
            'tracks': '/tracks',
            'track': '/tracks{/id}',
        }
    }

@app.route('/tracks', methods=['GET'])
@jsonify
def track_list():
    result = {
        'tracks': [],
        'uri': '/tracks',
    }

    for guid, track in tracks.iteritems():
        track['uri'] = '/tracks/{}'.format(guid)
        result['tracks'].append(track)
    return result



@app.route('/tracks/<guid>', methods=['GET'])
@jsonify
def track_info(guid):
    if guid not in tracks:
        raise NotFound('{} not found'.format(guid))

    result = tracks[guid]
    result['uri'] = '/tracks/{}'.format(guid)
    return result


if __name__ == '__main__':
    app.run(port=8002, debug=True)
