// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Kademlia.proto

package kademlia.grpc.builders;

public interface StoreRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:kademlia.grpc.builders.StoreRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.kademlia.grpc.builders.Node sender = 1;</code>
   */
  boolean hasSender();
  /**
   * <code>.kademlia.grpc.builders.Node sender = 1;</code>
   */
  Node getSender();
  /**
   * <code>.kademlia.grpc.builders.Node sender = 1;</code>
   */
  NodeOrBuilder getSenderOrBuilder();

  /**
   * <code>bytes key = 2;</code>
   */
  com.google.protobuf.ByteString getKey();

  /**
   * <code>bytes value = 3;</code>
   */
  com.google.protobuf.ByteString getValue();
}
