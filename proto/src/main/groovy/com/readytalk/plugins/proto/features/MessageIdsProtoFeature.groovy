package com.readytalk.plugins.proto.features

class MessageIdsProtoFeature extends ProtoFeatureBase {

  File idsOut
  File idsIn
  File plugin

  void configure() {
    args.addAll (
      "--plugin=protoc-gen-ids=${getPlugin()}",
      "--ids_out=${getIdsIn()}:${getIdsOut()}"
    )
  }

}
