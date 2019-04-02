import functools
import json
from flask import make_response
import os


def jsonify(fn):
    @functools.wraps(fn)
    def inner(*args, **kwargs):
        result = fn(*args, **kwargs)
        resp = make_response(json.dumps(result, indent=4))
        resp.headers['Content-Type'] = 'application/json'
        return resp
    return inner


def root():
    return os.path.dirname(os.path.realpath(__file__ + '/..'))

